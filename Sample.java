import ca.uhn.fhir.context.FhirContext;
        import ca.uhn.fhir.rest.client.api.IGenericClient;
        import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
        import org.hl7.fhir.r4.model.Bundle;
        import org.hl7.fhir.r4.model.Patient;

        import java.io.BufferedReader;
        import java.io.InputStreamReader;
        import java.util.Collections;
        import java.util.Comparator;
        import java.util.List;
        import java.util.stream.Collectors;

public class SampleClient {

    public static void main(String[] theArgs) {

        // Create a FHIR client
        FhirContext fhirContext = FhirContext.forR4();
        IGenericClient client = fhirContext.newRestfulGenericClient("http://hapi.fhir.org/baseR4");
        client.registerInterceptor(new LoggingInterceptor(false));

        Bundle response = client.search()
                .forResource(Patient.class)/**/
                .count(10)
                .returnBundle(Bundle.class)
                .execute();


        //List<Bundle.BundleEntryComponent> allEntry = response.getEntry().stream().collect(Collectors.toList());

        List<Bundle.BundleEntryComponent> allEntry = response.getEntry()
                .stream()
                .collect(Collectors.toList());

        //allEntry.sort();



/*
        List<Bundle.BundleEntryComponent> allSorted = Collections.sort(allEntry, new Comparator<Bundle.BundleEntryComponent>() {
                    @Override
                    public int compare(Bundle.BundleEntryComponent p1, Bundle.BundleEntryComponent p2) {

                        Patient patient = client.read().resource(Patient.class).withId(StoreId).execute();


                        return p1.
                        return 0;
                    }
                }
*/

        System.out.println("Size :" + allEntry.size());

        // Load the next page
//        org.hl7.fhir.r4.model.Bundle nextPage = client
//                .loadPage()
//                .next(response)
//                .execute();


        for (int i=0 ; i < allEntry.size(); i++) {
            System.out.println("Int i " + i);

            String StoreId = allEntry.get(i).getResource().getId();
            // Read a Patient
            Patient patient = client.read().resource(Patient.class).withId(StoreId).execute();

            if (patient.getName().get(0).getGiven() != null) {
                System.out.println("name :" + patient.getName().get(0).getGiven());
            }

            if (patient.getBirthDateElement() != null) {
                System.out.println("birth date :" + patient.getBirthDateElement());
            }

            if (patient.getName().get(0).getFamily() != null) {
                System.out.println("family name : " + patient.getName().get(0).getFamily());
            }

        }



    } //main

} //class
