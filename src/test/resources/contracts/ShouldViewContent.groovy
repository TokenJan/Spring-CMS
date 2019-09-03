package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return all contents"
    request{
        method GET()
        url("/api/contents/1")
    }
    response {
        body(
                id: 1,
                name: "this is title",
                status: "DRAFT"
        )
        status 200
    }
}