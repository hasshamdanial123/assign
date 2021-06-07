import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.IOException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeRESTController.class)
public class TestRESTController {
 
  @Autowired
  private MockMvc mvc;
  
  @Test
  public void sendMessageTest() throws Exception 
      {
       mvc.perform( MockMvcRequestBuilders
       .get("/v1/api/sqs")
       .accept(MediaType.APPLICATION_JSON))
       .andDo(print())
       .andExpect(status().isOk())
      }
@Test
  public void PostMessageTest() throws Exception 
    {
     mvc.perform( MockMvcRequestBuilders
       .post("/v1/api/sqs")
       .content(asJsonString("Testing POST request"))
       .contentType(MediaType.APPLICATION_JSON)
       .accept(MediaType.APPLICATION_JSON))
       .andExpect(status().isCreated())
}
 
}
