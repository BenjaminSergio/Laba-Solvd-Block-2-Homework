package com.solvd.airline.db;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Singleton holder for the {@link SqlSessionFactory}.
 *
 * Builds the factory exactly once at first access via the Holder idiom (lazy,
 * thread-safe, no double-checked-locking ceremony). Reads
 * {@code mybatis-config.xml} from the classpath; that config in turn pulls
 * {@code db.properties} for URL + credentials.
 *
 * Lecture anchor — Act 1 slide 9: SqlSessionFactoryBuilder is one-shot;
 * SqlSessionFactory is held for the JVM's lifetime; SqlSession is the
 * short-lived per-request handle and is never cached here.
 */
public final class MyBatisSessionFactory {

    private static final String CONFIG_RESOURCE = "mybatis-config.xml";

    private MyBatisSessionFactory() { }

    private static final class Holder {
        static final SqlSessionFactory INSTANCE = build();

        private static SqlSessionFactory build() {
            try (InputStream cfg = Resources.getResourceAsStream(CONFIG_RESOURCE)) {
                if (cfg == null) {
                    throw new IllegalStateException(
                            "Missing classpath resource: " + CONFIG_RESOURCE);
                }
                return new SqlSessionFactoryBuilder().build(cfg);
            } catch (IOException e) {
                throw new IllegalStateException(
                        "Failed to build SqlSessionFactory from " + CONFIG_RESOURCE, e);
            }
        }
    }

    public static SqlSessionFactory getInstance() {
        return Holder.INSTANCE;
    }
}
