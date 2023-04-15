package ro.netinstructor.utility;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class Utilities {

    private static String URL_ANAF = "https://webservicesp.anaf.ro/PlatitorTvaRest/api/v7/ws/tva"; //TODO schimba link-ul

    public static long createID() {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        long id = zdt.toInstant().toEpochMilli();
        return id;
    }

    /**
     * The method verify if a numeric String passed as parameter is a valid CIF
     *
     * @param cif string - The CIF to be verified
     * @return boolean value : true - if "cif" is valid,
     * : false if not
     */
    public static boolean verificareCif(String cif) {
        if ("".equals(cif) || cif == null) return false;
        cif = cif.trim();
        String prefix = "ro";
        String cifPrefix = cif.substring(0, 2).toLowerCase();
        if (prefix.equals(cifPrefix)) {
            cif = cif.substring(2).trim();
        }

        if (cif.length() > 10 || cif.length() < 2) return false;
        if (!isCifNumeric(cif)) return false;

        int cifraDeControl = Integer.parseInt(cif) % 10;
        int cifToverify = Integer.parseInt(cif) / 10;
        int result = 0;
        int CIF_CONTROL_NUMBER = 753217532;
        while (cifToverify > 0) {
            result += (cifToverify % 10) * (CIF_CONTROL_NUMBER % 10);
            cifToverify = cifToverify / 10;
            CIF_CONTROL_NUMBER = CIF_CONTROL_NUMBER / 10;
        }

        int rest = result * 10 % 11;
        if (rest == 10) {
            rest = 0;
        }

        return rest == cifraDeControl;
    }

    /**
     * Verify based on the params if the company is real from ANAF .
     * Method brings information based on the cif in JSON format, and compares if the name
     * inserted is equal with the value from ANAF
     *
     * @param cif      String
     * @param denumire String
     * @return true if the params match the values from ANAF
     */
    public static boolean verificareCifAnaf(String denumire, String cif) {
        LocalDate date = LocalDate.now();
        HttpResponse<String> response = Unirest.post(URL_ANAF)
                .header("Content-Type", "application/json")
                .body("[{\n\"cui\": " + cif + ",\n\"data\":\"" + date + "\"\n}]")
                .asString();

        JSONObject dateFirma = null;
        JSONObject numeFirma = null;
        try {
            JSONParser parse = new JSONParser();
            JSONObject dataObject = (JSONObject) parse.parse(response.getBody());
            JSONArray arr = (JSONArray) dataObject.get("found");
            dateFirma = (JSONObject) arr.get(0);
            numeFirma = (JSONObject) dateFirma.get("date_generale");

        } catch (org.json.simple.parser.ParseException e) {
            System.out.println(e.getStackTrace());
        }
        Unirest.shutDown();
        String nameToCompare = numeFirma.get("denumire")
                .toString().replace(" S.R.L.", "")
                .replace(" S.A.","")
                .replace(" SRL", "")
                .replace(" SA", "").trim();
        return denumire.equalsIgnoreCase(nameToCompare);
    }

    private static boolean isCifNumeric(String cif) {

        String prefix = "ro";
        String cifPrefix = cif.substring(0, 2).toLowerCase();
        if (prefix.equals(cifPrefix)) {
            cif = cif.substring(2).trim();
        }

        if (cif == null) return false;
        try {
            int n = Integer.parseInt(cif);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * The method verify if an numeric String passed as parameter is valid
     *
     * @param cnp string - The CNP to be verified
     * @return boolean value : true - if cnp is valid,
     * : false if not
     */
    public static boolean verificareCnp(String cnp) {
        if (cnp == null || cnp.equals("")) return false;
        if (!isCnpNumeric(cnp)) return false;
        if (cnp.length() != 13) return false;
        if (Integer.parseInt(cnp.substring(0, 1)) == 0) return false;
        if (!isValid(cnp.substring(1, 7))) return false;

        long cnpParsed = Long.parseLong(cnp.substring(0, cnp.length() - 1));
        long result = 0L;
        long CNP_CONTROL_NUMBER = 279146358279L;
        while (cnpParsed > 0) {
            result += (cnpParsed % 10) * (CNP_CONTROL_NUMBER % 10);
            cnpParsed = cnpParsed / 10;
            CNP_CONTROL_NUMBER = CNP_CONTROL_NUMBER / 10;
        }
        long rest = result % 11;
        if (rest == 10) rest = 1L;

        return rest == Integer.parseInt(cnp.substring(12));

    }

    private static boolean isCnpNumeric(String str) {
        try {
            Long n = Long.parseLong(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private static boolean isValid(String str) {
        String date = str.substring(0, 2) + "/" + str.substring(2, 4) + "/" + str.substring(4);
        String format = "yy/MM/dd";
        Date parseDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            parseDate = sdf.parse(date);
            if (!date.equals(sdf.format(parseDate))) {
                parseDate = null;
            }
        } catch (ParseException ex) {
        }
        return parseDate != null;
    }
}
