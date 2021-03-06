package com.github.goldin.spock.extensions

import com.github.goldin.spock.extensions.testdir.TestDir
import com.github.goldin.spock.extensions.time.Time
import com.github.goldin.spock.extensions.with.With
import spock.lang.FailsWith
import spock.lang.Specification


/**
 * {@code @With} extension test spec.
 */
@Time( min = 0, max = 10000 )
@With({ [ 'http://gradle.org/'.toURL(), 'http://groovy.codehaus.org/' ] })
class WithSpecGlobalObjects extends Specification
{
    @SuppressWarnings( 'StatelessClass' )
    @TestDir File testDir


    @Time( min = 0, max = 300 )
    @With({ [ 'string', [ 1 : 2 ], [ true ] ] })
    def 'regular test method' () {

        expect:
        true
        size() == 6
        containsKey( 1 )
        first()
    }


    @Time( min = 0, max = 300 )
    @With({ [ 'string', [ 1 : 3 ], [ true ] ] })
    @FailsWith( value = RuntimeException, reason = 'No @With object responds to method [aaaa]' )
    def 'failing test method' () {

        expect:
        true
        size() == 6
        containsKey( 1 )
        first()
        aaaa()
    }


    @Time( min = 100, max = 5000 )
    def 'URL test method' () {

        when:
        new File( testDir, 'data.txt' ).write( text )

        then:
        new File( testDir, 'data.txt' ).text.size() > ( 9 * 1024 )
    }


    @Time( min = 100, max = 5000 )
    @With({ [ null ] })
    def 'null test method' () {

        expect:
        // 'http://gradle.org/'.toURL()
        protocol
        protocol == 'http'
        host
        host == 'gradle.org'
        port
        port == -1
        bytes
        bytes.size() > ( 9 * 1024 )
        // http://groovy.codehaus.org/
        chars
        chars.size() == 27
        size()
        size()       == 27
    }


    @Time( min = 0, max = 200 )
    @With({ null })
//    Doesn't fail due to global objects
//    @FailsWith( value = RuntimeException, reason = 'Only null objects specified to @With' )
    def 'single null test method' () {

        expect:
        size()
        size() == 27
    }


    @Time( min = 0, max = 200 )
    @With({ [ null ] })
//    Doesn't fail due to global objects
//    @FailsWith( value = RuntimeException, reason = 'Only null objects specified to @With' )
    def 'single null test method - 2' () {

        expect:
        size()
        size() == 27
    }


    @Time( min = 0, max = 100 )
    @With({ true })
    def 'Boolean true test method' () {

        expect:
        value
        value == true
        booleanValue()
        booleanValue() == true
    }


    @Time( min = 0, max = 100 )
    @With({ false })
    def 'Boolean false test method' () {

        expect:
      ! value
        value == false
      ! booleanValue()
        booleanValue() == false
    }
}
