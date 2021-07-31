package org.springframework.samples.petclinic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

//https://thepracticaldeveloper.com/2017/07/31/guide-spring-boot-controller-tests/
//https://stackoverflow.com/questions/42822506/runwithcucumber-class-and-autowired-mockmvc
// https://www.google.com/search?client=safari&rls=en&q=spring+integrationtests&ie=UTF-8&oe=UTF-8

/**
 * 
 * @author marco.mangan@gmail.com
 *
 */
@ContextConfiguration(classes = PetClinicApplication.class)
public class CucumberSteps extends CucumberIntegrationTests {

	/**
	 * 
	 */
	private ResultActions results;

	@When("^I search for all pet owners$")
	public void i_search_for_all_pet_owners() throws Throwable {
		initMockMvc();
		results = mockMvc.perform(get("/owners"));

	}

	@Then("^a list of all owners is displayed$")
	public void a_list_of_all_owners_is_displayed() throws Throwable {
		results.andExpect(status().isOk()).andExpect(view().name("owners/ownersList"));
	}

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	Person person;
	Set<ConstraintViolation<Person>> constraintViolations;
	ConstraintViolation<Person> violation;

	@Given("^a person with empty first name$")
	public void a_person_with_empty_first_name() throws Throwable {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		person = new Person();
		person.setFirstName("");
		person.setLastName("smith");
	}

	@Given("^\"([^\"]*)\" as last name$")
	public void as_last_name(String arg1) throws Throwable {
		person.setLastName("smith");
	}

	@When("^person is validated$")
	public void person_is_validated() throws Throwable {
		Validator validator = createValidator();
		constraintViolations = validator.validate(person);
	}

	@Then("^invalid field must be \"firstName$")
	public void invalid_field_must_be_firstName() throws Throwable {
		assertThat(constraintViolations).hasSize(1);
		violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("firstName");
	}

	@Then("^message must be ”must not be empty\"$")
	public void message_must_be_must_not_be_empty() throws Throwable {
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

}