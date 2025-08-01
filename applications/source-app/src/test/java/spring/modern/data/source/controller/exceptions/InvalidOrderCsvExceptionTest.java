

package spring.modern.data.source.controller.exceptions;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class InvalidOrderCsvExceptionTest {

    @Test
    void  setException() {

        var errorMsg = "Exception";
        assertThat(new InvalidOrderCsvException(
                new NumberFormatException(errorMsg)).getMessage()).contains(errorMsg);

    }
}