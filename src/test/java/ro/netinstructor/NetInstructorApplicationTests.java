package ro.netinstructor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.netinstructor.services.CompanyService;
import ro.netinstructor.utility.Utilities;

@SpringBootTest
class NetInstructorApplicationTests {


	@Test
	public void testCIfValidationWhenNoletteres() {
		String cif = "37123525";

		Assertions.assertTrue(Utilities.verificareCif(cif));
	}

	@Test
	public void testCIfValidationWhenletteresOrNull() {
		String cif1 = "Ro 42263169";
		String cif2 = "RO42263169";
		String cif3 = "Ro   42263169";
		String cif4 = "  Ro   42263169  ";
		String cif5 = "rol42263169";
		String cif6 = "albaneagra";
		String cif7 = "";
		String cif8 = null;
		Assertions.assertTrue(Utilities.verificareCif(cif1));
		Assertions.assertTrue(Utilities.verificareCif(cif2));
		Assertions.assertTrue(Utilities.verificareCif(cif3));
		Assertions.assertTrue(Utilities.verificareCif(cif4));
		Assertions.assertFalse(Utilities.verificareCif(cif5));
		Assertions.assertFalse(Utilities.verificareCif(cif6));
		Assertions.assertFalse(Utilities.verificareCif(cif7));
		Assertions.assertFalse(Utilities.verificareCif(cif8));
	}

}
