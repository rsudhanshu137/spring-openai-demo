package com.javatechie.rag;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RAGPdfDataLoader {

    private final VectorStore vectorStore;

    @Value("classpath:Sudhanshu_Ranjan_Resume_PhonePe.pdf")
    Resource pdfResource;

    public RAGPdfDataLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadPdfeDataToVectorStore(){

        TikaDocumentReader documentReader=new TikaDocumentReader(pdfResource);
        List<Document>documents= documentReader.get();
        TextSplitter textSplitter= TokenTextSplitter.builder()
                .withChunkSize(200)
                .withMaxNumChunks(200)
                .build();
        List<Document> documentChunks = textSplitter.split(documents);
        vectorStore.add(documentChunks);



    }
}
