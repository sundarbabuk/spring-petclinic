package org.springframework.samples.petclinic;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * 
 * @author marco.mangan@gmail.com
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "features")
public class CucumberTests {
}