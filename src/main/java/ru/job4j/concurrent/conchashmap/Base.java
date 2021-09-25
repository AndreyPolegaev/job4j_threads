package ru.job4j.concurrent.conchashmap;

/**
 * ID - уникальный идентификатор. В системе будет только один объект с таким ID.
 * version - определяет достоверность версии в кеше.
 * Поле name - это поля бизнес модели. В нашем примере это одно поле name. Это поле имеет get set методы.
 * В кеше должна быть возможность проверять актуальность данных. Для этого в модели данных используется поле int version
 * Это поле должно увеличиваться на единицу каждый раз, когда модель изменили, то есть вызвали метод update.
 * Даже если все поля остались не измененными поле version должно увеличиться на 1.
 */

public class Base {

    private final int id;
    private int version;
    private String name;

    public Base(int id, int version) {
        this.id = id;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return " id=" + id
                + ", version=" + version
                + ", name='" + name;
    }
}
