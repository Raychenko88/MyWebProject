package com.mainacad.dao;

import com.mainacad.dao.model.ItemDTO;
import com.mainacad.model.Cart;
import com.mainacad.model.Item;
import com.mainacad.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

	public static Item save(Item item) {
		String sql = "INSERT INTO items " + "(name, item_code, price, availability) " + "VALUES (?, ?, ?, ?)";
		String sequenceSQL = "SELECT currval(pg_get_serial_sequence('items','id'))";

		int result = 0;
		try (Connection connection = ConnectionToDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				PreparedStatement sequenceStatement = connection.prepareStatement(sequenceSQL)) {
			preparedStatement.setString(1, item.getName());
			preparedStatement.setString(2, item.getCode());
			preparedStatement.setInt(3, item.getPrice());
			preparedStatement.setInt(4, item.getAvailability());
			result = preparedStatement.executeUpdate();

			if (result == 1) {
				ResultSet resultSet = sequenceStatement.executeQuery();
				while (resultSet.next()) {
					Integer id = resultSet.getInt(1);
					item.setId(id);
					break;
				}
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}

	public static List<Item> getAll() {
		String sql = "SELECT * FROM items";
		List<Item> items = new ArrayList<>();
		try (Connection connection = ConnectionToDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				ResultSet resultSet = preparedStatement.executeQuery();) {
			while (resultSet.next()) {
				Item item = getItemFromTable(resultSet);
				items.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}

	// TODO test and Orders ВЕЩ 
	public static List<Item> getAllByCart(Cart cart) {
		String sql = "SELECT items.* FROM items " + "JOIN orders o ON o.item_id = i.id " + "JOIN carts c ON c.id = o.cart_id "
				+ "WHERE c.id = ? ";
		List<Item> items = new ArrayList<>();
		try (Connection connection = ConnectionToDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setInt(1, cart.getId());
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Item item = getItemFromTable(resultSet);
				items.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}

	public static List<Item> getAllAvailable() {
		String sql = "SELECT * FROM items WHERE availability > 0";
		List<Item> items = new ArrayList<>();
		try (Connection connection = ConnectionToDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				ResultSet resultSet = preparedStatement.executeQuery();) {
			while (resultSet.next()) {
				Item item = getItemFromTable(resultSet);
				items.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}

	public static List<ItemDTO> getAllByUserAndPeriod(User user, Long timeFrom, Long timeTo) {
		String sql = "SELECT i.id as itemid, i.name as item_name, i.price as item_price FROM items i "
				+ "JOIN orders o ON o.item_id = i.id " + "JOIN carts c ON c.id = o.cart_id " + "WHERE c.user_id = ? "
				+ "AND c.creation_time >=?  AND c.creation_time <=? " + "AND c.status = 2";
		List<ItemDTO> itemDTOS = new ArrayList<>();
		try (Connection connection = ConnectionToDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setInt(1, user.getId());
			preparedStatement.setLong(2, timeFrom);
			preparedStatement.setLong(3, timeTo);

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				ItemDTO itemDTO = new ItemDTO(resultSet.getInt("itemid"), resultSet.getString("item_name"),
						resultSet.getInt("item_price"));
				itemDTOS.add(itemDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemDTOS;
	}

	public static Item getById(Integer id) {
		String sql = "SELECT * FROM items WHERE id = ?";
		try (Connection connection = ConnectionToDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Item item = getItemFromTable(resultSet);
				return item;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Item update(Item item) {
		String sql = "UPDATE items SET " + "name=?, " + "item_code=?, " + "price=?, " + "availability=?" + "WHERE id = ?";
		try (Connection connection = ConnectionToDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, item.getName());
			preparedStatement.setString(2, item.getCode());
			preparedStatement.setInt(3, item.getPrice());
			preparedStatement.setInt(4, item.getAvailability());
			preparedStatement.setInt(5, item.getId());

			int result = preparedStatement.executeUpdate();
			if (result == 1) {
				return item;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void delete(Integer id) {
		String sql = "DELETE FROM items WHERE id = ?";
		try (Connection connection = ConnectionToDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static Item getItemFromTable(ResultSet resultSet) throws SQLException {
		return new Item(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("item_code"),
				resultSet.getInt("price"), resultSet.getInt("availability"));
	}

	public static List<Item> findByItemCode(String code) {
		List<Item> items = new ArrayList<>();

		String statement = "SELECT * FROM items WHERE item_code=?";

		try (Connection connection = ConnectionToDB.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(statement)) {

			preparedStatement.setString(1, code);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				items.add(getItemFromResultSetItem(resultSet));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return items;
	}

	private static Item getItemFromResultSetItem(ResultSet resultSet) throws SQLException {
		Item item = new Item();

		item.setId(resultSet.getInt("id"));
		item.setCode(resultSet.getString("item_code"));
		item.setName(resultSet.getString("name"));
		item.setPrice(resultSet.getInt("price"));
		item.setPrice(resultSet.getInt("availability"));

		return item;
	}
}
