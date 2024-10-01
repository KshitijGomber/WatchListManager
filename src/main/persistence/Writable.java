package persistence;

import org.json.JSONObject;

/**
 * This interface is adapted from the Writable interface of JsonSerializationDemo.
 */
public interface Writable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
