package pl.margeb.check_please.bill.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.margeb.check_please.bill.domain.model.BillOperation;
import pl.margeb.check_please.bill.service.BillOperationService;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/groups/{group-id}/bills/{bill-id}/operations")
public class BillOperationApiController {

    private final BillOperationService billOperationService;

    public BillOperationApiController(BillOperationService billOperationService) {
        this.billOperationService = billOperationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    BillOperation createBillOperation(@PathVariable("group-id")UUID groupId, @PathVariable("bill-id") UUID billId, @RequestBody BillOperation billOperation){
        return billOperationService.createBillOperation(billId, billOperation);
    }

    @GetMapping
    List<BillOperation> getBillOperations(@PathVariable("group-id") UUID groupId, @PathVariable("bill-id") UUID billId){
        return billOperationService.getBillOperations(billId);
    }

    @GetMapping("{billOperation-id}")
    BillOperation getBillOperation(@PathVariable("group-id") UUID groupId,
                          @PathVariable("bill-id")UUID billId,
                          @PathVariable("billOperation-id") UUID billOperationId){
        return billOperationService.getBillOperation(billOperationId);
    }

    @PutMapping("{billOperation-id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    BillOperation updateBillOperation(@PathVariable("group-id")UUID groupId,
                             @PathVariable("bill-id")UUID billId,
                             @PathVariable("billOperation-id") UUID billOperationId,
                             @RequestBody BillOperation billOperation){
        return billOperationService.updateBillOperation(billOperationId, billOperation);
    }

    @DeleteMapping("{billOperation-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteBillOperation(@PathVariable("group-id")UUID groupId,
                             @PathVariable("bill-id")UUID billId,
                             @PathVariable("billOperation-id") UUID billOperationId){
        billOperationService.deleteBillOperation(billOperationId);
    }
}
