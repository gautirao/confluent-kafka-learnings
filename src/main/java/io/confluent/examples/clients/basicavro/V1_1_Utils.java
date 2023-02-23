package io.confluent.examples.clients.basicavro;

import com.natwest.event.v1_0.PayloadValue;
import com.natwest.event.v1_1.EventEnvelope;

public class V1_1_Utils {

    public static com.natwest.event.v1_1.CreditEvent createCreditEvent(int count) {

        return com.natwest.event.v1_1.CreditEvent.newBuilder().setEventUniqueId(count+"").setSchemaId("CreditEvent_V1_1").setEnvelope(getEnvelope(count)).setPayload(getPayload(count)).build();
    }

    public static com.natwest.event.v1_0.EventPayload getPayload(int count) {
        return com.natwest.event.v1_0.EventPayload.newBuilder().setSchemaId("paylaod_"+count).setValue(getPayloadValue(count)).build();
    }

    public static com.natwest.event.v1_0.PayloadValue getPayloadValue(int count) {
        return PayloadValue.newBuilder().setCreditAmount(count).setJourney("marketing").build();
    }

    public static EventEnvelope getEnvelope(int count) {
        return com.natwest.event.v1_1.EventEnvelope.newBuilder().setEventType(com.natwest.event.v1_1.EventType.APPROVAL_EVENT).setSchemaId("envelope_"+count).setLineOfBusiness(com.natwest.event.v1_1.lineOfBusiness.MKTG).setInitiatedBy("user").setInitiatedTimestamp("123456").build();

    }
}
