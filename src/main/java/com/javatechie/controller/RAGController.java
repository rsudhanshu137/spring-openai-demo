//package com.javatechie.controller;
//
//import com.javatechie.rag.SimpleFilteringPostProcessor;
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
//import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer;
//import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
//import org.springframework.ai.vectorstore.VectorStore;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.Resource;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/rag")
//public class RAGController {
//
//    private final ChatClient chatClient;
//    private final ChatClient.Builder chatClientBuilder;
//    private final VectorStore vectorStore;
//
//    @Value("classpath:/prompts/systemDataPrompt.st")
//    private Resource template;
//
//    public RAGController(ChatClient chatClient, ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
//        this.chatClient = chatClient;
//        this.chatClientBuilder = chatClientBuilder;
//        this.vectorStore = vectorStore;
//    }
//
//    @GetMapping("/connect")
//    public String connectToRagAI(@RequestParam String prompt, @RequestParam String username){
//
//        // R + A -> Retrieval and Augmentation handled by the advisor
//        RetrievalAugmentationAdvisor retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder()
//                .queryTransformers(TranslationQueryTransformer.builder()
//                        .chatClientBuilder(chatClientBuilder)
//                        .targetLanguage("english")
//                        .build())
//                .documentRetriever(VectorStoreDocumentRetriever.builder()
//                        .vectorStore(vectorStore)
//                        .topK(5)
//                        .similarityThreshold(0.3)
//                        .build())
//                .documentPostProcessors(new SimpleFilteringPostProcessor())
//                .build();
//
//        // G- Generation
//        return chatClient.prompt()
//                .system(promptSystemSpec -> promptSystemSpec.text(template)
//                        .param("username", username)
//                        .param("documents", ""))
//                .advisors(retrievalAugmentationAdvisor)
//                .advisors(advisorSpec -> advisorSpec
//                        .param("username", username))
//                .user(prompt)
//                .call()
//                .content();
//
//    }
//}
