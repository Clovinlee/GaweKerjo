package com.example.gawekerjo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gawekerjo.model.*

@Database(entities = [
    AchievementItem::class,
    ChatItem::class,
    CommentItem::class,
    CompanyItem::class,
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
    Rememberme::class,
], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract val achievementDao:AchievementDao
    abstract val chatDao:ChatDao
    abstract val commentDao:CommentDao
    abstract val companyDao:CompanyDao
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

    companion object{
        var DB:AppDatabase?=null
        fun Build(c: Context):AppDatabase{
            if (DB == null) {
                DB= Room.databaseBuilder(c,AppDatabase::class.java,"gawekerjo2").build()
            }
            return DB!!
        }

    }
}