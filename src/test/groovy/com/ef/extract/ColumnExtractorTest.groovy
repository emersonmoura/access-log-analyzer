package com.ef.extract

import static LogColumn.*
import spock.lang.Specification

class ColumnExtractorTest extends Specification{

    private date = '2017-01-01 00:00:11.763'
    private ip = '192.168.234.82'
    private request = '"GET / HTTP/1.1"'
    private status = '200'
    private userAgent = '"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0"'
    private String line = "${date}|${ip}|${request}|${status}|${userAgent}"
    private ColumnExtractor extractor

    def setup(){
        extractor = new ColumnExtractor()
    }

    def 'given a valid line when extract date should be returned'(){
        when:
        def extract = extractor.columnOnLine(DATE, line)

        then:
        extract == date
    }

    def 'given a valid line when extract ip should be returned'(){
        when:
        def extract = extractor.columnOnLine(IP, line)

        then:
        extract == ip
    }

    def 'given a valid line when extract request should be returned'(){
        when:
        def extract = extractor.columnOnLine(REQUEST, line)

        then:
        extract == request
    }

    def 'given a valid line when extract status should be returned'(){
        when:
        def extract = extractor.columnOnLine(STATUS, line)

        then:
        extract == status
    }

    def 'given a valid line when extract userAgent should be returned'(){
        when:
        def extract = extractor.columnOnLine(USER_AGENT, line)

        then:
        extract == userAgent
    }

    def 'given a line without right size when extract should return error'(){
        given:
        String invalidLine = '2017-01-01 00:00:11.763|192.168.234.82|"GET / HTTP/1.1"|"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0"'

        when:
        extractor.columnOnLine(REQUEST, invalidLine)

        then:
        def ex = thrown(IllegalStateException)
        ex.message == 'Line without right size'
    }


}
