package com.example.cloud;

import com.example.cloud.model.Organization;
import com.example.cloud.model.Person;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.cache.affinity.AffinityKey;
import org.apache.ignite.lifecycle.LifecycleBean;
import org.apache.ignite.lifecycle.LifecycleEventType;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.h2.jdbcx.JdbcConnectionPool;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2017/6/29 0029.
 */
public class CacheLoader implements LifecycleBean {

	private static final String ORG_CACHE = CacheLoader.class.getSimpleName() + "Organizations";
	@IgniteInstanceResource
	transient Ignite ignite;
	transient JdbcTemplate jdbcTemplate;

	CacheLoader() {
		jdbcTemplate = new JdbcTemplate(JdbcConnectionPool.create("jdbc:h2:tcp://localhost/~/test", "sa", ""));
	}

	@Override
	public void onLifecycleEvent(LifecycleEventType evt) throws IgniteException {

// Organizations.
		Organization org1 = new Organization("ApacheIgnite");
		Organization org2 = new Organization("Other");
		// People.
		Person p1 = new Person(org1, "John", "Doe", 2000, "John Doe has Master Degree.");
		Person p2 = new Person(org1, "Jane", "Doe", 1000, "Jane Doe has Bachelor Degree.");
		Person p3 = new Person(org2, "John", "Smith", 1000, "John Smith has Bachelor Degree.");
		Person p4 = new Person(org2, "Jane", "Smith", 2000, "Jane Smith has Master Degree.");


		if ( evt == LifecycleEventType.AFTER_NODE_START ) {

			IgniteCache<AffinityKey<Long>, Person> cache = ignite.getOrCreateCache("person");
			List<Person> persons = jdbcTemplate.query("select * from persons", new RowMapper<Person>() {
				@Override
				public Person mapRow(ResultSet resultSet, int i) throws SQLException {
					return new Person(org1,resultSet.getString(1), resultSet.getString(2), resultSet.getLong(3),resultSet.getString(1));

				}
			});

			for ( Person o : persons ) {
				cache.put(o.key(), o);
			}
		}

	}
}
