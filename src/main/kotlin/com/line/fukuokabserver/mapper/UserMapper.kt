package com.line.fukuokabserver.mapper

import com.line.fukuokabserver.dto.UserDTO
import org.apache.ibatis.annotations.*

@Mapper
interface UserMapper {

    @Select(
            """
        SELECT id, name, userId, mail, hitokoto FROM users WHERE id=#{id}
        """
    )
    @Results(value = arrayOf(
            Result(id = true, property = "id", column = "id"),
            Result(property = "name", column = "name"),
            Result(property = "userId", column = "userId"),
            Result(property = "mail", column = "mail"),
            Result(property = "hitokoto", column = "hitokoto")
    ))
    fun findById(id: Long): UserDTO

    @Select("SELECT id, name, userId, mail, hitokoto FROM users WHERE userId=#{userId}")
    @Results(value = arrayOf(
            Result(id = true, property = "id", column = "id"),
            Result(property = "name", column = "name"),
            Result(property = "userId", column = "userId"),
            Result(property = "mail", column = "mail"),
            Result(property = "hitokoto", column = "hitokoto")
    ))
    fun findByUserId(userId: String): UserDTO

    @Select("SELECT id, name, userId, mail, hitokoto FROM users WHERE id IN (SELECT friendId FROM friends WHERE userId=#{id})")
    @Results(value = arrayOf(
            Result(id = true, property = "id", column = "id"),
            Result(property = "name", column = "name"),
            Result(property = "userId", column = "userId"),
            Result(property = "mail", column = "mail"),
            Result(property = "hitokoto", column = "hitokoto")
    ))
    fun getFriends(id: Long): ArrayList<UserDTO>

    @Select("SELECT id, name, userId, mail, hitokoto FROM users WHERE mail=#{mail}")
    @Results(value = arrayOf(
            Result(id = true, property = "id", column = "id"),
            Result(property = "name", column = "name"),
            Result(property = "userId", column = "userId"),
            Result(property = "mail", column = "mail"),
            Result(property = "hitokoto", column = "hitokoto")
    ))
    fun findByMail(mail: String): UserDTO

    @Insert("INSERT INTO friends (userId, friendId) VALUES (#{id}, #{friendId})")
    fun addFriend(id: Long, friendId: Long)

    @Update("UPDATE friends SET channelId=#{channelId} WHERE (userId=#{id1} AND friendId=#{id2}) OR (userId=#{id2} AND friendId=#{id1})")
    fun addPersonalChannel(id1: Long, id2: Long, channelId: Long)

    @Select("SELECT channelId FROM friends WHERE userId=#{id1} AND friendId=#{id2}")
    fun getPersonalChannelId(id1: Long, id2: Long): Long?

    @Update("UPDATE users SET name=#{name}, hitokoto=#{hitokoto} WHERE id=#{id}")
    fun updateUser(userDTO: UserDTO)
}