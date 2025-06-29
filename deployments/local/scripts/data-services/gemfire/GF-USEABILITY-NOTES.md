127.0.0.1:6379> CLIENT LIST
ERR Unknown subcommand or wrong number of arguments for 'LIST'. Supported subcommands are: [SETNAME, GETNAME]




java.lang.IllegalArgumentException: DefaultSerializer requires a Serializable payload but received an object of type [spring.modern.data.domains.customer.Promotion]
at org.springframework.core.serializer.DefaultSerializer.serialize(DefaultSerializer


JSON has large payloads


body:[B@5f746944 channel:{"@class":"spring.modern.data.domains.customer.Promotion","id":"string","products":["java.util.ArrayList",[{"@class":"spring.modern.data.domains.customer.Product","id":"string","name":"string"}]]}
