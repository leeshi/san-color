package ml.sansejin.sancolor.controller;

import io.swagger.annotations.ApiOperation;
import ml.sansejin.sancolor.entity.UserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userInfo")
public class UserInfoController {
    /**
     * 添加一个带有指定信息的用户
     * @param userInfo
     * @return boolean
     */
    @ApiOperation("添加一个用户")
    @PostMapping("")
    public boolean addUserInfo(@RequestBody UserInfo userInfo){

        return true;
    }

    /**
     * 删除一个指定id的用户
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public boolean deleteUserInfo(@PathVariable Long id){
        return true;
    }

    @ApiOperation("更新用户资料")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUerInfo(@PathVariable long id, @RequestBody UserInfo userInfo){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
