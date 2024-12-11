import filter.CORSFilter;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import resource.AppExceptionMapper;
import resource.CityResource;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class JaxRsApplication extends Application {
    private final Set<Class<?>> classes = new HashSet<>();

    public JaxRsApplication() {
        classes.add(CityResource.class);
        classes.add(CORSFilter.class);
        classes.add(AppExceptionMapper.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}