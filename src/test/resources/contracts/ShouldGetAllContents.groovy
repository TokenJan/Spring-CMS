package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return all contents"
    request{
        method GET()
        url("/api/contents") {}
    }
    response {
        body([])
        status 200
    }
}