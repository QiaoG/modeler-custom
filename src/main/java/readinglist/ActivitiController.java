package readinglist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
@RequestMapping("/activiti")
public class ActivitiController implements ModelDataJsonConstants {

    @Autowired
    private RepositoryService repositoryService;

    @RequestMapping(method = RequestMethod.GET)
    public String newModel(HttpServletResponse resp) throws IOException {
        return "newActivitiModel";
    }

    @RequestMapping(method = RequestMethod.POST)
    public void toEditor(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        String name = request.getParameter("name");
        String des = request.getParameter("description");
        String modelId = mock(name,des);

        resp.sendRedirect("modeler.html?modelId="+modelId);
    }

    private String mock(String name,String des){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);
            Model modelData = repositoryService.newModel();

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(MODEL_NAME, name);
            modelObjectNode.put(MODEL_REVISION, 1);
            String description = null;
            if (StringUtils.isNotEmpty(des)) {
                description = des;
            } else {
                description = "";
            }
            modelObjectNode.put(MODEL_DESCRIPTION, description);
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(name);

            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
            return modelData.getId();
        } catch(Exception e) {
            e.printStackTrace();
            return "0";
        }
    }
}
