package ro.netinstructor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ro.netinstructor.utility.Utilities;

@SpringBootTest
@DisplayName("Teste Aplicatie Net-Instructor")
class NetInstructorApplicationTests {

    @Test
    @DisplayName("Test verificare CIF fara litere OK")
    public void testCIfValidationWhenNoLetters() {
        String cif = "20312023";
        Assertions.assertTrue(Utilities.verificareCif(cif));
    }

    @Test
    @DisplayName("Test de verificare CIF ok ")
    public void testCIfValidationWhenLettersOk() {
        String cif1 = "Ro 20312023";
        String cif2 = "RO42263169";
        String cif3 = "Ro   42263169";
        String cif4 = "  Ro   42263169  ";

        Assertions.assertTrue(Utilities.verificareCif(cif1));
        Assertions.assertTrue(Utilities.verificareCif(cif2));
        Assertions.assertTrue(Utilities.verificareCif(cif3));
        Assertions.assertTrue(Utilities.verificareCif(cif4));

    }

    @Test
    @DisplayName("Test de verificare CIF not OK or Null ")
    public void testCIfValidationWhenLettersOrNullNotOk() {
        String cif5 = "rol42263169";
        String cif6 = "albaneagra";
        String cif7 = "";
        String cif8 = null;

        Assertions.assertFalse(Utilities.verificareCif(cif5));
        Assertions.assertFalse(Utilities.verificareCif(cif6));
        Assertions.assertFalse(Utilities.verificareCif(cif7));
        Assertions.assertFalse(Utilities.verificareCif(cif8));
    }

    @Test
    @DisplayName("Test de verificare CNP OK")
    public void verificareCnp() {
        String cnp1 = "1740412120733";

        Assertions.assertTrue(Utilities.verificareCnp(cnp1));
    }

    @Test
    @DisplayName("Test de verificare CNP format de date gresit")
    public void verificareCnpDataNotOk() {
        String cnp1 = "1713462120733";

        Assertions.assertFalse(Utilities.verificareCnp(cnp1));
    }

    @Test
    @DisplayName("Test de verificare CNP Null sau empty string")
    public void verificareCnpNull() {
        String cnp1 = null;
        String cnp2 = "";

        Assertions.assertFalse(Utilities.verificareCnp(cnp1));
        Assertions.assertFalse(Utilities.verificareCnp(cnp2));
    }

    @Test
    @DisplayName("Test verificare companie in ANAF")
    public void testUnirest() {

        Assertions.assertTrue(Utilities.verificareCifAnaf("13839790", "INFO stamPILe SRL"));
        Assertions.assertFalse(Utilities.verificareCifAnaf("13839790", "Denumire SRL"));
    }

}
