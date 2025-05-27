package com.efederation.Service.impl;

import com.efederation.DTO.ApiResponse;
import com.efederation.DTO.CreateNodeRequest;
import com.efederation.Enums.NodeType;
import com.efederation.Exception.NodeRequiresParentException;
import com.efederation.Exception.ParentNodeNotFoundException;
import com.efederation.Model.TreeNode;
import com.efederation.Repository.TreeNodeRepository;
import com.efederation.Service.TreeNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TreeNodeServiceImpl implements TreeNodeService {

    private final TreeNodeRepository treeNodeRepository;

    @Autowired
    public TreeNodeServiceImpl(TreeNodeRepository treeNodeRepository) {
        this.treeNodeRepository = treeNodeRepository;
    }

    public ApiResponse<TreeNode> addNode(CreateNodeRequest request) throws ParentNodeNotFoundException, NodeRequiresParentException {
        if(request.getAvailableOptions() != null && request.getAvailableOptions().length > 0 && request.getParentNodeId() == null) {
            throw new NodeRequiresParentException("To be a root, the node cannot have available options");
        }
        // verify parent exists per id passed
        TreeNode parent = null;
        if(request.getParentNodeId() != null) {
            parent = treeNodeRepository
                    .findById(request.getParentNodeId())
                    .orElseThrow(() -> new ParentNodeNotFoundException("Parent node not found"));
        }
        TreeNode newNode = TreeNode
                .builder()
                .name(request.getName())
                .type(NodeType.valueOf(request.getType()))
                .text(request.getText())
                .availableOptions(request.getAvailableOptions())
                .parent(parent)
                .build();
        treeNodeRepository.save(newNode);
        ApiResponse<TreeNode> response = new ApiResponse<>();
        response.setMessage("Successfully added node");
        response.setData(newNode);
        response.setStatus(HttpStatus.OK.toString());
        return response;
    }
}
