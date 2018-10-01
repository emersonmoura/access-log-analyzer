package com.ef.execute


import static com.ef.di.DependencyFactory.createInput
import com.ef.input.InputValidator
import spock.lang.Specification

import java.util.stream.Stream

class ParserExecutorTest  extends Specification {

    ParserExecutor parserExecutor
    InputValidator validatorMock = Mock(InputValidator)
    ParserFile fileMock = Mock(ParserFile)

    def setup(){
        parserExecutor = new ParserExecutor(validatorMock, fileMock, createInput())
        fileMock.lines(_) >> Stream.empty()
    }

    def 'given a valid line when extract date should be returned'(){
        given:
        String expectedIp = '192.168.234.82'
        String path = '/path/to/file'
        Stream fileContent = Stream.of("""2017-01-01 00:00:11.763|${expectedIp}|"GET / HTTP/1.1"|200|"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0""".toString())
        fileMock.lines(path) >> fileContent
        String[] args = ["--accesslog=${path}",'--startDate=2017-01-01.00:00:00','--duration=hourly','--threshold=1']

        when:
        def result = parserExecutor.execute(args)

        then:
        result.find() == expectedIp
    }
}
