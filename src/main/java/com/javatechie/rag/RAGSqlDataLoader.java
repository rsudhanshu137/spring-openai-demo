package com.javatechie.rag;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class RAGSqlDataLoader {

    private final VectorStore vectorStore;

    private final JdbcTemplate jdbcTemplate;

    public RAGSqlDataLoader(VectorStore vectorStore, JdbcTemplate jdbcTemplate) {
        this.vectorStore = vectorStore;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void loadSupportTicketsToVectorStore() {

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                "SELECT id, category, question, answer FROM support_tickets");

        List<Document> documents = rows.stream()
                .map(row -> {
                    String content = "Category: " + row.get("category")
                            + "\nQuestion: " + row.get("question")
                            + "\nAnswer: " + row.get("answer");
                    return new Document(content, Map.of(
                            "source", "support_tickets",
                            "ticketId", String.valueOf(row.get("id")),
                            "category", String.valueOf(row.get("category"))
                    ));
                })
                .toList();

        TextSplitter textSplitter = TokenTextSplitter.builder()
                .withChunkSize(20)
                .withMaxNumChunks(200)
                .build();
        List<Document> documentChunks = textSplitter.split(documents);
        vectorStore.add(documentChunks);
    }
}
