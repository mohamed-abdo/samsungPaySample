package com.noonpay.sample.samsungPay.APIHelper;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by abdo on 3/3/2018.
 */

public class Helper {
    private static final String TAG = "Helper";
    private final Gson gson = new Gson();

    class FlattenDeserializer implements JsonDeserializer<Map<String, String>> {
        @Override
        public Map<String, String> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Map<String, String> map = new LinkedHashMap<>();

            if (json.isJsonArray()) {
                for (JsonElement e : json.getAsJsonArray()) {
                    map.putAll(deserialize(e, typeOfT, context));
                }
            } else if (json.isJsonObject()) {
                for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
                    if (entry.getValue().isJsonPrimitive()) {
                        map.put(entry.getKey(), entry.getValue().getAsString());
                    } else {
                        map.putAll(deserialize(entry.getValue(), typeOfT, context));
                    }
                }
            }
            return map;
        }
    }

    public byte[] SerializeObject(Object model) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            String serialization = gson.toJson(model);
            out = new ObjectOutputStream(bos);
            out.writeObject(serialization);
            out.flush();
            return bos.toByteArray();
        } catch (IOException error) {
            Log.e(TAG, error.getMessage());
            return null;
        } finally {
            try {
                bos.close();
            } catch (IOException error) {
                Log.e(TAG, error.getMessage());
            }
        }
    }

    public Object DesrializeObject(byte[] object, Class type) {
        ByteArrayInputStream bis = new ByteArrayInputStream(object);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            Object rawObject = in.readObject();
            return gson.fromJson(rawObject.toString(), type);
        } catch (ClassNotFoundException error) {
            Log.e(TAG, error.getMessage());
            return null;
        } catch (IOException error) {
            Log.e(TAG, error.getMessage());
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException error) {
                Log.e(TAG, error.getMessage());
            }
        }
    }

}
