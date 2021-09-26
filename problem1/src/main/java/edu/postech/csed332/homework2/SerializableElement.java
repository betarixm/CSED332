package edu.postech.csed332.homework2;

import java.util.Map;

public abstract class SerializableElement extends Element {
    public abstract Map<String, Object> serializer();
}
