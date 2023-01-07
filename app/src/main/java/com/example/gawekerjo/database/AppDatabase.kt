package com.example.gawekerjo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gawekerjo.model.*
import com.example.gawekerjo.model.chat.ChatItem
import com.example.gawekerjo.model.country.CountryItem
import com.example.gawekerjo.model.education.EducationItem
import com.example.gawekerjo.model.follow.FollowItem
import com.example.gawekerjo.model.skill.SkillItem
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.model.userchat.UserChatItem
import com.example.gawekerjo.model.userskill.UserSkillItem

@Database(entities = [
    AchievementItem::class,
    ChatItem::class,
    CommentItem::class,
    EducationItem::class,
    ExperienceItem::class,
    LanguageItem::class,
    OfferItem::class,
    OfferSkillItem::class,
    OrganizationItem::class,
    PostCommentItem::class,
    PostItem::class,
    PostLikeItem::class,
    SkillItem::class,
    UserItem::class,
    UserChatItem::class,
    UserLanguageItem::class,
    UserSkillItem::class,
    FollowItem::class,
    CountryItem::class,
], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract val achievementDao:AchievementDao
    abstract val chatDao:ChatDao
    abstract val commentDao:CommentDao
    abstract val educationDao:EducationDao
    abstract val experienceDao:ExperienceDao
    abstract val languageDao:LanguageDao
    abstract val offerDao:OfferDao
    abstract val offerskillDao:OfferSkillDao
    abstract val organizationDao:OrganizationDao
    abstract val postcommentDao:PostCommentDao
    abstract val postDao:PostDao
    abstract val postlikeDao:PostLikeDao
    abstract val skillDao:SkillDao
    abstract val userchatDao:UserChatDao
    abstract val userDao:UserDao
    abstract val userlanguageDao:UserLanguageDao
    abstract val userskillDao:UserSkillDao
    abstract val followDao: FollowDao
    abstract val countryDao: CountryDao

    companion object{
        var DB:AppDatabase?=null
        fun Build(c: Context):AppDatabase{
            if (DB == null) {
                DB= Room.databaseBuilder(c,AppDatabase::class.java,"gawekerjo").build()
            }
            return DB!!
        }

    }
}