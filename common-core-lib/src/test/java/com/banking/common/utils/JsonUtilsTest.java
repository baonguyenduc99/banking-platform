import com.banking.common.exception.CoreException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

package com.banking.common.utils;

class JsonUtilsTest {

    static class Dummy {
        public String name;
        public int age;

        public Dummy() {
        }

        public Dummy(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (!(obj instanceof Dummy))
                return false;
            Dummy other = (Dummy) obj;
            return age == other.age && (name == null ? other.name == null : name.equals(other.name));
        }
    }

    @Test
    void testToJson_validObject() {
        Dummy dummy = new Dummy("Alice", 30);
        String json = JsonUtils.toJson(dummy);
        assertTrue(json.contains("\"name\":\"Alice\""));
        assertTrue(json.contains("\"age\":30"));
    }

    @Test
    void testToJson_nullObject() {
        String json = JsonUtils.toJson(null);
        assertEquals("null", json);
    }

    @Test
    void testFromJson_validJson() {
        String json = "{\"name\":\"Bob\",\"age\":25}";
        Dummy dummy = JsonUtils.fromJson(json, Dummy.class);
        assertEquals(new Dummy("Bob", 25), dummy);
    }

    @Test
    void testFromJson_invalidJson_throwsCoreException() {
        String invalidJson = "{\"name\":\"Bob\",\"age\":}";
        CoreException ex = assertThrows(CoreException.class, () -> JsonUtils.fromJson(invalidJson, Dummy.class));
        assertTrue(ex.getMessage().contains("Failed to deserialize JSON"));
    }

    @Test
    void testIsValidJson_validJson() {
        String json = "{\"name\":\"Charlie\",\"age\":40}";
        assertTrue(JsonUtils.isValidJson(json));
    }

    @Test
    void testIsValidJson_invalidJson() {
        String invalidJson = "{\"name\":\"Charlie\",\"age\":}";
        assertFalse(JsonUtils.isValidJson(invalidJson));
    }

    @Test
    void testIsValidJson_nullString() {
        assertFalse(JsonUtils.isValidJson(null));
    }

    @Test
    void testIsValidJson_emptyString() {
        assertFalse(JsonUtils.isValidJson(""));
    }
}