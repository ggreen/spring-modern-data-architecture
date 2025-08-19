package spring.modern.data.architecture.valkey.console.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import spring.modern.data.architecture.valkey.console.domain.Command;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("valKey")
@Slf4j
public class ValKeyConsoleController {
    private final RedisTemplate<String, String> template;

    @GetMapping("lrange")
    public List<String> lrange(String key, long start, long end) {
        return template.opsForList().range(key,start,end);
    }


    @DeleteMapping("del")
    public long del(String... keys) {
        return template.delete(Arrays.asList(keys));
    }



    @DeleteMapping("flushall")
    public String flushall(){
        Object result = template.execute((RedisCallback<Object>) connection -> {
            return connection.execute("FLUSHALL");
        });

        return decorateResult(result);
    }


    /**
     * @param command the command to execute
     * @return the string results
     */
    @PostMapping("cmd")
    public String executeCommand(@RequestBody Command command){

        log.info("Executing command: {}",command);

        byte[][] argBytes;
        if(command.parameters() != null)
            argBytes = Arrays.stream(command.parameters()).map( String::getBytes).toArray(byte[][]::new);
        else {
            argBytes = new byte[0][0];
        }

        Object result = template.execute((RedisCallback<Object>) connection -> {
            return connection.execute(command.name(),argBytes);
        });

        return decorateResult(result);
    }

    /**
     * Convert command results to Stirng
     * @param result the Process command results
     * @return the formatted strongs
     */
    private static String decorateResult(Object result) {
        String results;
        if (result instanceof byte[]) {
            results = new String((byte[]) result, StandardCharsets.UTF_8);
        }
        else if(result instanceof List list)
        {
            return list.stream().map(item -> {
                        if (item instanceof byte[] itemBytes)
                            return new String(itemBytes);
                        else
                            return item;
                    }
            ).toList() + "";
        }
        else {
            results = String.valueOf(result);
        }

        return results;
    }
}
