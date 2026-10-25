package com.javatechie.rag;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RAGSampleDataLoader {

    private final VectorStore vectorStore;

    public RAGSampleDataLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadSampleDataToVectorStore(){
        List<String>sampleData=getSampleData();
        List<Document> documents=sampleData.stream()
                        .map(Document::new)
                         .toList();
        vectorStore.add(documents);
    }

    private List<String> getSampleData() {
        return List.of(
                // Cricket
                "India wins the cricket World Cup",
                "Virat Kohli scores a brilliant century",
                "IPL auction attracts record bids",
                "Rohit Sharma announces retirement from Test cricket",
                "India defeats Australia in a thrilling match",

                // Entertainment
                "New Bollywood movie breaks box office records",
                "Shah Rukh Khan announces his upcoming film",
                "Netflix releases a new web series",
                "Oscar nominations announced for the year",
                "Popular singer announces a world tour",

                // Politics
                "Government announces new economic policies",
                "Parliament debates the new education bill",
                "Election results declared in key states",
                "Prime Minister addresses the nation",
                "New infrastructure development project announced",

                // Science and Technology
                "NASA discovers a new planet beyond our solar system",
                "Scientists develop a breakthrough in renewable energy",
                "ISRO announces its next space mission",
                "Researchers make progress in quantum computing",
                "Artificial intelligence transforms scientific research"
        );
    }
}
