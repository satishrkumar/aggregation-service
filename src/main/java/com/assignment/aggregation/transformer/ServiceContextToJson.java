package com.assignment.aggregation.transformer;

import com.assignment.aggregation.model.ServiceContext;
import org.springframework.stereotype.Component;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.io.StringReader;
import java.util.List;

import static com.assignment.aggregation.model.ServiceType.*;

@Component
public class ServiceContextToJson {
    public String transform(List<ServiceContext> serviceContext) {
        final JsonObjectBuilder payload = Json.createObjectBuilder();
        final JsonObjectBuilder shipments = Json.createObjectBuilder();
        final JsonObjectBuilder track = Json.createObjectBuilder();
        final JsonObjectBuilder pricing = Json.createObjectBuilder();
        serviceContext.forEach(value -> {
            if (PRICING == value.getServiceType()) {
                pricing.add(value.getOriginalValue(), Json.createValue(Double.parseDouble((value.getResponse()))));
            } else if (SHIPMENTS == value.getServiceType()) {
                shipments.add(value.getOriginalValue(), Json.createReader(new StringReader(value.getResponse())).readArray());
            } else if (TRACK == value.getServiceType()) {
                track.add(value.getOriginalValue(), value.getResponse().replaceAll("\"", ""));
            }
        });

        payload.add(SHIPMENTS.getName(), shipments.build());
        payload.add(TRACK.getName(), track.build());
        payload.add(PRICING.getName(), pricing.build());

        return payload.build().toString();
    }
}
