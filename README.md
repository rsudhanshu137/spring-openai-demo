# Spring AI OpenAI Demo — AI Support Agent & RAG Playground

A Spring Boot 3 / Spring AI project demonstrating how to build an **AI-powered support agent** exposed as an **MCP (Model Context Protocol) server**, with multiple **Retrieval-Augmented Generation (RAG)** pipelines (in-memory samples, SQL-backed FAQ data, and PDF ingestion), **function/tool calling**, and real-time data lookups (weather, timezone).

Built to explore how a modern Java backend can act as a tool-provider for LLM-based agents (e.g. Claude Desktop, Claude Code, or any MCP-compatible client) rather than exposing a traditional REST API.

## Why this project

Most "Spring AI + OpenAI" demos stop at a single `/chat` endpoint. This project goes a step further and shows:

- How to turn plain Spring `@Service` methods into **LLM-callable tools** using Spring AI's `@Tool` annotations.
- How to serve those tools over **MCP (stdio)**, so any MCP client (Claude Desktop, IDE agents, etc.) can discover and invoke them directly — no REST controller required.
- Three different flavors of **RAG** data ingestion into a vector store (Qdrant): hardcoded samples, a MySQL-backed FAQ table, and a PDF document.
- A simple, realistic **domain model** (IT support tickets) with priority detection, SLA calculation, and auto-assignment logic.

## Architecture

```
                ┌─────────────────────────┐
                │   MCP Client (stdio)    │   e.g. Claude Desktop / Claude Code
                └───────────┬─────────────┘
                            │ MCP (stdio)
                ┌───────────▼─────────────┐
                │   Spring AI MCP Server   │
                │  (ai-support-mcp)        │
                ├──────────────────────────┤
                │ Tools (@Tool methods)    │
                │  • IT Support Ticket Tool│──► MySQL (tickets)
                │  • Real-Time Data Tool   │──► WeatherAPI.com
                ├──────────────────────────┤
                │ RAG Data Loaders         │
                │  • Sample data loader    │──► Qdrant (vector store)
                │  • SQL FAQ loader        │──► Qdrant (vector store)
                │  • PDF loader (Tika)     │──► Qdrant (vector store)
                └──────────────────────────┘
                            │
                ┌───────────▼─────────────┐
                │        OpenAI API        │
                └──────────────────────────┘
```

The app runs with `web-application-type: NONE` — it's a non-web process that speaks MCP over **stdio**, meant to be launched by an MCP-aware client rather than hit with `curl`.

The `service/` package contains three independent chat-service implementations built while exploring different ways to talk to the LLM, each with a matching (currently commented-out) REST controller for reference:

| Service | Demonstrates | Status |
|---|---|---|
| `OpenAIChatService` | Plain OpenAI chat, plus tool-calling chat where the model is handed `RealTimeDataTool` + `ITSupoortTicketTool` directly via `ChatClient.tools(...)` | Reference (commented out) |
| `MultiModelChatService` | Routing the same prompt to either an OpenAI or an Ollama-backed `ChatClient` | Reference (commented out) |
| `SupportTicketService` | Real ticket business logic — creation, escalation, comments, priority/category detection, SLA calculation | **Active** — backs `ITSupoortTicketTool`, exposed live over MCP |

In other words: the REST-controller + direct `ChatClient.tools(...)` approach and the MCP-server + `@Tool`-annotated-class approach are two independent ways of exposing the *same* underlying capabilities (chat, weather/timezone lookups, ticket management) — the MCP path is the one currently wired up and running, while the controller/service path is kept in the codebase as a side-by-side reference for the pre-MCP approach.

## Tech Stack

| Layer | Technology |
|---|---|
| Language / Runtime | Java 21 |
| Framework | Spring Boot 3.5, Spring AI 1.1.8 |
| LLM Provider | OpenAI (via `spring-ai-starter-model-openai`) |
| Agent Protocol | MCP — Model Context Protocol (`spring-ai-starter-mcp-server-webmvc`, stdio transport) |
| Vector Store | Qdrant |
| RAG | `spring-ai-rag`, `spring-ai-advisors-vector-store`, `spring-ai-tika-document-reader` |
| Persistence | Spring Data JPA, MySQL (tickets), H2 (runtime), JDBC (FAQ loader) |
| API Docs | springdoc-openapi (Swagger UI) |
| Build | Maven (wrapper included) |
| Infra | Docker Compose (Qdrant + MySQL) |

## Features

### 1. MCP-exposed tools (agent-callable functions)
- **IT Support Ticket Tool** (`ITSupoortTicketTool`)
  - Create a ticket (auto-detects priority & category, auto-assigns engineer, calculates SLA)
  - Add a comment to a ticket
  - Escalate a ticket to high priority
  - Fetch full ticket details
  - List all tickets
- **Real-Time Data Tool** (`RealTimeDataTool`)
  - Get current weather for a city (WeatherAPI.com)
  - Get timezone info for a city (WeatherAPI.com)

Any MCP client can connect to this server, see these tools, and let the LLM decide when to call them — e.g. *"Create a support ticket for a failed payment from john@doe.com"* results in the model calling `createTicket(...)` automatically.

### 2. Retrieval-Augmented Generation (RAG)
Three independent data loaders populate a Qdrant vector store on startup:
- `RAGSampleDataLoader` — small hardcoded dataset (cricket, entertainment, politics, tech) for quick RAG experimentation.
- `RAGSqlDataLoader` — loads a customer-support FAQ table (orders, refunds, payments, shipping) from MySQL and embeds it.
- `RAGPdfDataLoader` — parses a PDF via Apache Tika, splits it into token-sized chunks, and embeds it (used here with a sample resume PDF to demonstrate document ingestion).

A `SimpleFilteringPostProcessor` shows how to post-filter retrieved documents before they reach the prompt (e.g. excluding results containing a given keyword).

### 3. Support Ticket Domain Model
JPA-backed `SupportTicket` entity with `TicketStatus`, `Priority`, and `Category` enums, and a `TicketHelper` utility that auto-detects priority/category from free text, assigns an engineer, and computes SLA deadlines.

## Project Structure

```
src/main/java/com/javatechie/
├── configuration/     # ChatClient & tool-callback wiring
├── controller/        # (reference) REST controllers — currently commented out
├── entity/            # JPA entities & enums (SupportTicket, Priority, Category, TicketStatus)
├── rag/               # Vector store data loaders + post-processor
├── repository/        # Spring Data JPA repository
├── service/           # Business logic (tickets, chat)
├── tools/             # @Tool-annotated classes exposed via MCP
└── util/              # TicketHelper (priority/category/SLA logic)

src/main/resources/
├── application.yaml
├── schema.sql / data.sql   # FAQ table bootstrap for RAG
└── prompts/systemDataPrompt.st
```

## Getting Started

### Prerequisites
- Java 21
- Docker & Docker Compose (for Qdrant + MySQL)
- An [OpenAI API key](https://platform.openai.com/api-keys)
- A [WeatherAPI.com](https://www.weatherapi.com/) API key (free tier works)

### 1. Configure environment variables
Create a `.env` file in the project root (already git-ignored):

```env
OPENAI_API_KEY=sk-...
WEATHER_API_KEY=your-weatherapi-key
```

### 2. Start infrastructure
```bash
docker-compose up -d
```
This starts:
- **Qdrant** on `6333`/`6334` (vector store)
- **MySQL** on `3307` (support ticket data)

### 3. Run the application
```bash
./mvnw spring-boot:run
```

On startup, the app loads sample/SQL/PDF data into Qdrant and starts the MCP server over stdio.

### 4. Connect an MCP client
Point an MCP-compatible client (e.g. Claude Desktop) at the built jar, for example:

```json
{
  "mcpServers": {
    "ai-support-mcp": {
      "command": "java",
      "args": ["-jar", "/path/to/ai-support-mcp.jar"]
    }
  }
}
```

Once connected, the client can list and invoke the tools in `tools/` directly — no HTTP calls needed.

## Configuration Reference

Key properties in `application.yaml`:

| Property | Purpose |
|---|---|
| `spring.ai.openai.api-key` | OpenAI credential (from `.env`) |
| `spring.ai.mcp.server.*` | MCP server name/type/stdio transport |
| `spring.ai.vectorstore.qdrant.*` | Qdrant connection + collection name |
| `spring.datasource.*` | MySQL connection for support tickets |
| `weather.api.key` | WeatherAPI.com credential (from `.env`) |
| `server.port` | HTTP port (unused while `web-application-type: NONE`) |

## Author

**Sudhanshu Ranjan** — [sudhanshukapil6@gmail.com](mailto:sudhanshukapil6@gmail.com)
