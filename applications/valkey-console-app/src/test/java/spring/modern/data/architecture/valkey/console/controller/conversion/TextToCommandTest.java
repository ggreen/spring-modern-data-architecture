package spring.modern.data.architecture.valkey.console.controller.conversion;

import org.junit.jupiter.api.Test;
import spring.modern.data.architecture.valkey.console.domain.Command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TextToCommandTest {


    private final static TextToCommand subject = new TextToCommand();

    @Test
    void keys() {

        String[] expectedParameters = {"*"};
        Command expectedCommand = Command.builder().name("keys").parameters(expectedParameters).build();

        var actual = subject.convert("keys *");

        assertThat(actual).isNotNull();
        assertThat(actual.name()).isEqualTo(expectedCommand.name());
        assertThat(actual.parameters()).isNotNull();
        assertThat(actual.parameters().length).isEqualTo(1);
        assertThat(actual.parameters()).contains(expectedParameters[0]);

    }

    @Test
    void empty() {
        assertThat(subject.convert(null)).isNull();

        assertThat(subject.convert("")).isNull();


    }

    @Test
    void flushall() {

        Command expectedCommand = Command.builder().name("flushall").build();

        var actual = subject.convert("flushall");

        assertThat(actual).isNotNull();
        assertThat(actual.name()).isEqualTo(expectedCommand.name());
        assertThat(actual.parameters()).isEmpty();

    }
}