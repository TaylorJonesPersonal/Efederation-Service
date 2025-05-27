package com.efederation.Controller;

import com.efederation.DTO.ApiResponse;
import com.efederation.DTO.CreateNodeRequest;
import com.efederation.Exception.ApiError;
import com.efederation.Exception.ChildNodeNotFoundException;
import com.efederation.Exception.NodeRequiresParentException;
import com.efederation.Exception.ParentNodeNotFoundException;
import com.efederation.Model.TreeNode;
import com.efederation.Service.TreeNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;


@RestController
@RequestMapping("/api/node")
public class TreeNodeController {

    private final TreeNodeService treeNodeService;

    @Autowired
    public TreeNodeController(TreeNodeService treeNodeService) {
        this.treeNodeService = treeNodeService;
    }

    @ExceptionHandler({
            NodeRequiresParentException.class,
            ChildNodeNotFoundException.class,
            ParentNodeNotFoundException.class
    })
    @ResponseBody
    public ResponseEntity<Object> handleException(
            Exception ex,
            WebRequest webRequest) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "An error occurred.");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @PostMapping(
            value = "/v1/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponse<TreeNode>> addNode(@RequestBody CreateNodeRequest request)
            throws ParentNodeNotFoundException, ChildNodeNotFoundException, NodeRequiresParentException {
        ApiResponse<TreeNode> response = treeNodeService.addNode(request);
        return ResponseEntity.ok(response);
    }
}
