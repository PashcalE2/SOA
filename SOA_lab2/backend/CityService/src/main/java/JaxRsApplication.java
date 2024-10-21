import filter.CORSFilter;
import resource.CityResource;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class JaxRsApplication extends Application {
    private final Set<Class<?>> classes = new HashSet<>();

    public JaxRsApplication() {
        classes.add(CityResource.class);
        classes.add(CORSFilter.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}