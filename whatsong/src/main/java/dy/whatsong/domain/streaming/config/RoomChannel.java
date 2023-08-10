package dy.whatsong.domain.streaming.config;

import org.springframework.stereotype.Service;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.logging.Logger;

@Service
public class RoomChannel {
    private EmitterProcessor<String> processor;
    private Flux<String> flux;
    private FluxSink<String> sink;
    private Runnable closeCallback;
    private Logger logger;

    public RoomChannel() {
        processor= EmitterProcessor.create();
        this.sink=processor.sink();
        this.flux=processor
                .doOnCancel(() -> {
                    logger.info("doOnCancel, downstream " + processor.downstreamCount());
                    if (processor.downstreamCount() == 1) close();
                })
                .doOnTerminate(() -> {
                    logger.info("doOnTerminate, downstream " + processor.downstreamCount());
                });
    }

    public void send(String message) {
        sink.next(message);
    }

    public Flux<String> toFlux() {
        return flux;
    }

    private void close() {
        if (closeCallback != null) closeCallback.run();
        sink.complete();
    }

    public RoomChannel onClose(Runnable closeCallback) {
        this.closeCallback = closeCallback;
        return this;
    }
}
