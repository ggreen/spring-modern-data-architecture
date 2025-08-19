package spring.modern.data.architecture.valkey.console.domain;

import lombok.Builder;

@Builder
public record Command(String name, String[] parameters) {
}
