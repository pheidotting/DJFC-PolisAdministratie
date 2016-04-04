package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.repository.PolisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.collect.Iterables.filter;

public abstract class AbstractService<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);

    public abstract PolisRepository getRepository();

//    public List<T> alles(SoortEntiteit soortEntiteit, Long parentId) {
//        LOGGER.debug("alles soortEntiteit {} parentId {}", soortEntiteit, parentId);
//
//        return getRepository().alles(soortEntiteit, parentId);
//    }

    public T lees(Long id) {
        return (T) getRepository().lees(id);
    }

//    public void opslaan(T adres) {
//        getRepository().opslaan(Lists.newArrayList(adres));
//    }

}
