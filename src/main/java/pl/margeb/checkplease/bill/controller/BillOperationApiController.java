package pl.margeb.checkplease.bill.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.margeb.checkplease.bill.domain.model.BillOperation;
import pl.margeb.checkplease.bill.service.BillOperationService;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/groups/{group-id}/bills/{bill-id}/operations")
public class BillOperationApiController {

    private final BillOperationService billOperationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    BillOperation createBillOperation(@PathVariable("group-id")UUID groupId, @PathVariable("bill-id") UUID billId, @RequestBody BillOperation billOperation){
        return billOperationService.createBillOperation(billId, billOperation);
    }

    @GetMapping
    List<BillOperation> getBillOperations(@PathVariable("group-id") UUID groupId, @PathVariable("bill-id") UUID billId){
        return billOperationService.getBillOperations(billId);
    }

    @GetMapping("{bill-operation-id}")
    BillOperation getBillOperation(@PathVariable("group-id") UUID groupId,
                                   @PathVariable("bill-id")UUID billId,
                                   @PathVariable("bill-operation-id") UUID billOperationId){
        return billOperationService.getBillOperation(billOperationId);
    }

    @PutMapping("{bill-operation-id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    BillOperation updateBillOperation(@PathVariable("group-id")UUID groupId,
                                      @PathVariable("bill-id")UUID billId,
                                      @PathVariable("bill-operation-id") UUID billOperationId,
                                      @RequestBody BillOperation billOperation){
        return billOperationService.updateBillOperation(billOperationId, billOperation);
    }

    @DeleteMapping("{bill-operation-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteBillOperation(@PathVariable("group-id")UUID groupId,
                             @PathVariable("bill-id")UUID billId,
                             @PathVariable("bill-operation-id") UUID billOperationId){
        billOperationService.deleteBillOperation(groupId, billOperationId);
    }
}
