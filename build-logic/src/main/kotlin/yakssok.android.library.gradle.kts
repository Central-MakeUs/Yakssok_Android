import com.pillsquad.yakssok.configureCoroutineAndroid
import com.pillsquad.yakssok.configureHiltAndroid
import com.pillsquad.yakssok.configureJUnitAndroid
import com.pillsquad.yakssok.configureKotlinAndroid

plugins {
    id("com.android.library")
    id("yakssok.verify.detekt")
}

configureKotlinAndroid()
configureCoroutineAndroid()
configureHiltAndroid()
configureJUnitAndroid()