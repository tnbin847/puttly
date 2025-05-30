package com.tnbin.puttly.global.common.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link CodeEnum}을 구현한 {@link Enum}과 데이터베이스의 컬럼 값 간의 매핑을 처리하는 타입 핸들러.
 * <p>{@link Enum}에 정의된 {@code code}를 데이터베이스에 저장하거나 데이터베이스의 컬럼 값을 {@link Enum}으로 변환한다.</p>
 * 
 * @param <E>   타입 핸들러에서 처리할 {@link Enum} 타입
 */

@MappedTypes(CodeEnum.class)
public class CodeEnumTypeHandler<E extends Enum<E> & CodeEnum> extends BaseTypeHandler<E> {

    private final Class<E> type;

    public CodeEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null!");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return from(rs.getString(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return from(rs.getString(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return from(cs.getString(columnIndex));
    }

    /**
     * 데이터베이스의 코드값과 일치하는 {@link Enum} 상수를 찾아 반환한다.
     *
     * @param code  데이터베이스로부터 가져온 코드값
     * @return      {@code code}값과 일치한 상수값이 존재할 경우 해당 상수값의 {@link Enum}을, 존재하지 않을 경우 {@code null}을 반환
     */
    private E from(String code) {
        try {
            E[] constants = type.getEnumConstants();
            for (E constant : constants) {
                if (constant.getCode().equals(code)) {
                    return constant;
                }
            }
            return null;
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot convert '" + code + "' to '" + type.getSimpleName() + "'!", e);
        }
    }
}