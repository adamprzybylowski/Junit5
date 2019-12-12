### JUnit5, AssertJ, Mockito

1. Testy jednostkowe

    1. Przypadek testowy
        - jak przetestować omlet 
        - jak przetestować samochód

    1. Ćwiczenie
        - napisz na tablicy przypadki testowe dla maszyny z napojami
        - przypadki pozytywne i negatywne
        - testujemy zachowanie, nie metody

    1. Terminologia
        - test suite / zestaw testów
        - test case / przypadek testowy
        - assertion / asercja (dosłownie twierdzenie / zapewnienie)

    1. Inne typy testów
        - integracyjne
        - end-to-end

1. JUnit

        git clone https://github.com/infoshareacademy/jjdd8-materialy-junit.git
        git co before
        
    1. test suite, case, assertion
    
        - omów domenę simple
        - przykładowy przypadek testowy - com.infoshare.junit5.simple.PurchaseTests

    1. Jak uruchomić testy

        - maven i plugin surefire
                
                ...
                <plugin>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <version>2.22.2</version>
                </plugin>
                ...

        - konsola
                
                mvn test

        - IntelliJ
            - Ctrl+Shift+F10 - w zależności od umiejscowienia kursora uruchomi wybrany test lub wszystkie testy
            - Ctrl+F5 - uruchom ponownie

1. Adnotacje

    1. Jak napisać przypadek testowy?

        - @Test
        - arrange, act, assert
        - assertTrue(), assertFalse(), assertEquals(), assertSame()
        - fail()

	1. Co jeżeli kod powtarza się w każdym przypadku testowym?

        - @BeforeEach, @AfterEach - uruchom metodę przed/po każdym przypadku testowym
        - @BeforeAll, @AfterAll - uruchom metodę przed/po zestawie testów

	1. Czy można wyłączyć niektóre testy?
	
	    - @Disabled
	    
	        -com.infoshare.junit5.simple.PurchaseTests	
	            - should_not_dispense_product_unless_it_was_selected
	
	1. Zadania
        
        com.infoshare.junit5.simple.PurchaseTests	   
  
1. Kilka słów na temat konwencji nazw

	- klasy nazywamy zgodnie z konwencją maven-surefire-plugin
	
        - **/Test*.java
        - **/*Test.java
        - **/*Tests.java
        - **/*TestCase.java
	
	            class TestDispenser { //... }
	            class CalculatorTest { //... }
	            class DispanserTests { //... }
	            class VendingMachineTestCase { //... }
	            
    - IntelliJ
        - Ctrl+Shift+T - tworzy test dla wybranej klasy i jej metod publicznych
     
        - Nie chcecie testować metod. Chcecie testować zachowanie.

	- metody nazywamy tak, żeby można było zrozumieć co testują
	
	    - should_ExpectedBehavior_When_StateUnderTest

                public void should_dispenseProduct_when_givenExactAmount()
                    //...
                }

    	- when_StateUnderTest_Expect_ExpectedBehavior

                public void when_exactAmountInserted_expect_productDispensed() {
                    //...
                }
	 
    - https://dzone.com/articles/7-popular-unit-test-naming
	
	- To tylko konwencja, lepiej użyć @DisplayName 
	
1. Ćwiczenie
	
	1. Omówienie domeny 
	
	    - com.infoshare.junit5.StandardVendingMachine
	    - com.infoshare.junit5.CoinBox
	    - com.infoshare.junit5.ProductBox
	    - com.infoshare.junit5.domain.Dispenser
	   
	1. Jak inaczej poprawić wyświetlanie wyników?

	    - @Nested
	    
	        - TODO com.infoshare.junit5.PurchaseTests
	
    1. Co się stanie, jeżeli w teście jednostkowym poleci wyjątek?
    
        - test nie przejdzie, zostanie oznaczony jako błąd 
        
                assertThrows(CustomException.class, ()-> {
                    machine.dispense();
                });

1. Assercje

	https://junit.org/junit5/docs/current/api/org/junit/jupiter/api/Assertions.html
	
	- assertNotNull
	- assertEquals
	- assertSame
	- assertThrows
	- assertAll
	
    1. Co się stanie, jeżeli w teście jest więcej assercji i pierwsza nie przejdzie?
    
        - test nie przejdzie, zostanie oznaczony jako porażka
        - kolejne assercje zostaną zignorowane

    1. Ćwiczenia
	
1. AssertJ
	
	- https://assertj.github.io/doc/

    1. Dlaczego AssertJ
    
        - prosty w użyciu, ze względu na assercje dostosowane do typu obiektu 
            - IDE podpowiada metody
        - lepsze opisy błędów
        - tylko jeden import
        
                import static org.assertj.core.api.Assertions.*; 

	1. Ćwiczenia
	
	    - com.infoshare.junit5.domain.DispenserTests
	        - TODO should_add_loaded_items_of_same_type
	        - should_throw_out_of_item_exception_junit_style
	        
	    - 
		        
1. Testy parametryzowane

	- @ParametrizedTest, @EnumSource, @ValueSource, assertAll
	    
	    - com.infoshare.junit5.CoinBoxTests
	    - com.infoshare.junit5.domain.DispenserLoadingTests
	        - TODO should_load_and_count_value_of_all_products
	    
	- @CsvSource
	
	    - com.infoshare.junit5.domain.DispenserTests
	        - should_calculate_total_value_of_ones_and_twos
    	    - TODO should_calculate_total_value_of_fives_and_tens
	
	- @MethodSource, Arguments
	
	    - com.infoshare.junit5.domain.DispenserTests
	        - should_calculate_change
	        - TODO should_calculate_total_value_of_five_and_tens
	  
1. Opcjonalne
 
    - Co jeżeli chcemy uruchomić testy w innej kolejności?
        - @TestMethodOrder, @Order
            - com.infoshare.junit5.domain.BookTests
        
    - Co zrobić, jeżeli wykonanie metody powinno się skończyć w określonym czasie?
    	- @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
	
1. Mockito

    - https://site.mockito.org

    1. Cel i zasady mockowania
    
        * Do not mock types you don’t own
        * Don’t mock value objects
        * Don’t mock everything
    
    1. Zależności
        
            <dependency>
              <groupId>org.mockito</groupId>
              <artifactId>mockito-core</artifactId>
              <version>3.2.0</version>
            </dependency>
        
    1. Tworzenie i używanie mocków

        - adnotacja i metoda statyczna

        1. Mockowanie metody
        - bez zwracania wartości (void)
        - zwracanie wartości
        - rzucanie wyjątkiem

    1. Weryfikowanie, że mock został wywołany (verify())
        - weryfikowanie liczby wywołań
        - argument captor

1. Dokumentacja

    - https://github.com/Pragmatists/vending-machine-kata
    - https://junit.org/junit5/docs/current/user-guide/
    - https://assertj.github.io/doc/
    - https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html
    

---