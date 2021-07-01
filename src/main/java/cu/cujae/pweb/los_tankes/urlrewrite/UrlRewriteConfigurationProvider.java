package cu.cujae.pweb.los_tankes.urlrewrite;

import javax.servlet.ServletContext;

import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;



@RewriteConfiguration
public class UrlRewriteConfigurationProvider extends HttpConfigurationProvider{

	@Override
	public Configuration getConfiguration(ServletContext context) {
		return ConfigurationBuilder.begin()
				.addRule(Join.path("/security-users").to("/pages/security/users.jsf"))
				.addRule(Join.path("/welcome").to("/pages/welcome/welcome.jsf"))
				.addRule(Join.path("/login").to("/pages/security/login.jsf"))
				.addRule(Join.path("/country-countries").to("/pages/classes/country/countries.jsf"))
				.addRule(Join.path("/payment-payments").to("/pages/classes/payment/payments.jsf"))
				.addRule(Join.path("/state-statuses").to("/pages/classes/state/statuses.jsf"))
				.addRule(Join.path("/brand-brands").to("/pages/classes/brand/brands.jsf"))
				.addRule(Join.path("/driver-drivers").to("/pages/classes/driver/drivers.jsf"))
				.addRule(Join.path("/model-models").to("/pages/classes/model/models.jsf"))
				.addRule(Join.path("/car-cars").to("/pages/classes/car/cars.jsf"))
				.addRule(Join.path("/tourist-tourists").to("/pages/classes/tourist/tourists.jsf"))
				.addRule(Join.path("/contract-contracts").to("/pages/classes/contract/contracts.jsf"))
				.addRule(Join.path("/touristsByCountry").to("/pages/reports/touristsByCountry.jsf"))
				.addRule(Join.path("/carsByBrand").to("/pages/reports/carsByBrand.jsf"))
				.addRule(Join.path("/contractByTourist").to("/pages/reports/ContractxTourist.jsf"))
				.addRule(Join.path("/contractByDriver").to("/pages/reports/ContractxChofer.jsf"))
				;

	}

	@Override
	public int priority() {
		return 0;
	}

}
