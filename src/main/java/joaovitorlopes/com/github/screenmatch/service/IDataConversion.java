package joaovitorlopes.com.github.screenmatch.service;

public interface IDataConversion {
    // Using Generics "<T> T"
    <T> T getData(String json, Class<T> tClass);
}
