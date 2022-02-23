package ru.util.jwt

import com.typesafe.config.ConfigFactory

private val conf = ConfigFactory.load()
val JWT_SECRET: String = conf.getString("jwt.secret")
val JWT_ISSUER: String = conf.getString("jwt.issuer")
val JWT_AUDIENCE: String = conf.getString("jwt.audience")
val JWT_REALM: String = conf.getString("jwt.realm")