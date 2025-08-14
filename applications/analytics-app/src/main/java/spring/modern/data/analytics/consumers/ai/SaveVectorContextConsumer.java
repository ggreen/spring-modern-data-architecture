package spring.modern.data.analytics.consumers.ai;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
@Slf4j
public class SaveVectorContextConsumer implements Consumer<String> {
    private final VectorStore vectorStore;

    @Override
    public void accept(String context) {

        log.info("Save RAG context: {}",context);

        if(context == null || context.isEmpty())
            return;

        vectorStore.add(List.of(Document.builder()
                .text(context).build()));
    }
}
