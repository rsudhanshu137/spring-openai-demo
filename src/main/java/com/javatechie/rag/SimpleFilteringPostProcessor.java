package com.javatechie.rag;

import org.springframework.ai.document.Document;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.postretrieval.document.DocumentPostProcessor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class SimpleFilteringPostProcessor implements DocumentPostProcessor {
    @Override
    public List<Document> process(Query query, List<Document> documents) {
        System.out.println("Applying filter logic after retrieval");
        return documents.stream()
                .filter(doc->!doc.getText().contains("NASA"))
                .toList();
    }

    @Override
    public List<Document> apply(Query query, List<Document> documents) {
        return DocumentPostProcessor.super.apply(query, documents);
    }

    @Override
    public <V> BiFunction<Query, List<Document>, V> andThen(Function<? super List<Document>, ? extends V> after) {
        return DocumentPostProcessor.super.andThen(after);
    }
}
