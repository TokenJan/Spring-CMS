package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should view the content by id"
    request{
        method GET()
        url("/api/contents/1")
    }
    response {
        body("""{
                "id": 1,
                "name": "this is title",
                "status": "DRAFT",
                "contentAttributeList": [
                        {
                            "key": "name",
                            "value": "this is title",
                            "type": "Text"
                        }
                ]}
        """)
        status 200
    }
}