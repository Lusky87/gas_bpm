package com.hncis.controller.product;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hncis.books.vo.BgabGascbk01Dto;
import com.hncis.books.vo.BgabGascbk02Dto;
import com.hncis.books.vo.BgabGascbk03Dto;
import com.hncis.books.vo.BgabGascbk04Dto;
import com.hncis.common.Constant;
import com.hncis.common.base.JSONBaseArray;
import com.hncis.common.base.JSONBaseVO;
import com.hncis.common.exception.impl.HncisException;
import com.hncis.common.manager.CommonManager;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.BpmApiUtil;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonList;
import com.hncis.common.vo.CommonMessage;
import com.hncis.controller.AbstractController;
import com.hncis.product.manager.ProductManager;
import com.hncis.product.vo.BgabGascpd01Dto;
import com.hncis.product.vo.BgabGascpd02Dto;
import com.hncis.product.vo.BgabGascpd03Dto;
import com.hncis.product.vo.BgabGascpd04Dto;
import com.hncis.restCenter.vo.BgabGascrc01Dto;

@Controller
public class ProductController extends AbstractController{

	private static final String strStart = "Start time : ";
	private static final String strEnd = "End time : ";
	private static final String strDateFormat = "yyyy-MM-dd HH:mm:ss.SSS";

	@Autowired
    @Qualifier("productManagerImpl")
	private ProductManager productManager;

	@Autowired
	@Qualifier("commonManagerImpl")
	private CommonManager commonManager;
	
	/**
	 * 물품 대분류 저장
	 *
	 * @param req the req
	 * @param res the res
	 * @param iParams, uParams the param json array - 조건
	 * @return ModelAndView
	 * @throws hncisException the hncis exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/product/doSavePdToLrgList.do")
	public ModelAndView doSaveBkToLrgList(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="iParams", required=true) String iParams,
			@RequestParam(value="uParams", required=true) String uParams) throws HncisException{
		//조회조건 설정
		List<BgabGascpd03Dto> iList = (List<BgabGascpd03Dto>) getJsonToList(iParams, BgabGascpd03Dto.class);
		List<BgabGascpd03Dto> uList = (List<BgabGascpd03Dto>) getJsonToList(uParams, BgabGascpd03Dto.class);
		
		//수정
		productManager.savePdToLrgList(iList, uList);
		
		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	// 물품 대분류 조회
	@RequestMapping(value="/hncis/product/doSearchPdListToLrgInfo.do")
	public ModelAndView doSearchBkListToLrgInfo(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		
		BgabGascpd03Dto vo = (BgabGascpd03Dto)getJsonToBean(paramJson, BgabGascpd03Dto.class);
		
		CommonList list = new CommonList();
		//검색
		list.setRows(productManager.selectPdListToLrgInfo(vo));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
		
		return modelAndView;
	}

	
	// 물품 중분류 저장
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/product/doSavePdToMrgList.do")
	public ModelAndView doSavePdToMrgList(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="iParams", required=true) String iParams,
			@RequestParam(value="uParams", required=true) String uParams) throws HncisException{
		//조회조건 설정
		List<BgabGascpd04Dto> iList = (List<BgabGascpd04Dto>) getJsonToList(iParams, BgabGascpd04Dto.class);
		List<BgabGascpd04Dto> uList = (List<BgabGascpd04Dto>) getJsonToList(uParams, BgabGascpd04Dto.class);
		
		//수정
		productManager.savePdToMrgList(iList, uList);
		
		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	// 물품 중분류 조회
	@RequestMapping(value="/hncis/product/doSearchPdListToMrgInfo.do")
	public ModelAndView doSearchPdListToMrgInfo(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		
		BgabGascpd04Dto vo = (BgabGascpd04Dto)getJsonToBean(paramJson, BgabGascpd04Dto.class);
		
		CommonList list = new CommonList();
		//검색
		list.setRows(productManager.selectPdListToMrgInfo(vo));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
		
		return modelAndView;
	}
	
	/**
	 * 물품 대분류 삭제
	 *
	 * @param req the req
	 * @param res the res
	 * @param iParams, uParams the param json array - 조건
	 * @return ModelAndView
	 * @throws hncisException the hncis exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/product/doDeletePdToLrgList.do")
	public ModelAndView doDeletePdToLrgList(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="dParams", required=true) String dParams) throws HncisException{
		//조회조건 설정
		List<BgabGascpd03Dto> dList = (List<BgabGascpd03Dto>) getJsonToList(dParams, BgabGascpd03Dto.class);
		
		//수정
		productManager.deletePdToLrgList(dList);
		
		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	// 물품 중분류 삭제
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/product/doDeletePdToMrgList.do")
	public ModelAndView doDeletePdToMrgList(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="dParams", required=true) String dParams) throws HncisException{
		//조회조건 설정
		List<BgabGascpd04Dto> dList = (List<BgabGascpd04Dto>) getJsonToList(dParams, BgabGascpd04Dto.class);
		
		//수정
		productManager.deletePdToMrgList(dList);
		
		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	/**
	 *  대분류 콤보 데이터 조회
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/product/doSearchPdToLrgCombo.do")
	public ModelAndView doSearchPdToLrgCombo(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson", required=true) String paramJson) throws Exception{
		
		BgabGascpd03Dto vo = (BgabGascpd03Dto)getJsonToBean(paramJson, BgabGascpd03Dto.class);
 		
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		
		try{
	 		JSONBaseVO jso = new JSONBaseVO();
	 		JSONBaseVO json = null;
			JSONBaseArray  jsonArr = null;
			
			List<BgabGascpd03Dto> codeList = null;
			
			jsonArr = new JSONBaseArray();
			
			if(StringUtil.isNullToStrTrm(vo.getS_type()).equals("A")){
				json = new JSONBaseVO();
				json.put("name", HncisMessageSource.getMessage("total"));
				json.put("value", "");
				jsonArr.add(json);
			}else if(StringUtil.isNullToStrTrm(vo.getS_type()).equals("S")){
				json = new JSONBaseVO();
				json.put("name", HncisMessageSource.getMessage("select"));
				json.put("value", "");
				jsonArr.add(json);
			}
			
			codeList = productManager.selectPdToLrgCombo(vo);
			
			for(BgabGascpd03Dto targetBean : codeList){
				json = new JSONBaseVO();
				json.put("value", StringUtil.isNullToStrTrm(targetBean.getLrg_cd()));
				json.put("name", StringUtil.isNullToStrTrm(targetBean.getLrg_nm()));
				
				jsonArr.add(json);
			}
			
			jso.put("LRG_COMB", jsonArr);
			
			modelAndView.addObject(JSON_DATA_KEY, jso.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	/**
	 *  중분류 콤보 데이터 조회
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/product/doSearchPdToMrgCombo.do")
	public ModelAndView doSearchPdToMrgCombo(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson", required=true) String paramJson) throws Exception{
		
		BgabGascpd04Dto vo = (BgabGascpd04Dto)getJsonToBean(paramJson, BgabGascpd04Dto.class);
 		
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		
		try{
	 		JSONBaseVO jso = new JSONBaseVO();
	 		JSONBaseVO json = null;
			JSONBaseArray  jsonArr = null;
			
			List<BgabGascpd04Dto> codeList = null;
			
			jsonArr = new JSONBaseArray();
			
			if(StringUtil.isNullToStrTrm(vo.getS_type()).equals("A")){
				json = new JSONBaseVO();
				json.put("name", HncisMessageSource.getMessage("total"));
				json.put("value", "");
				jsonArr.add(json);
			}else if(StringUtil.isNullToStrTrm(vo.getS_type()).equals("S")){
				json = new JSONBaseVO();
				json.put("name", HncisMessageSource.getMessage("select"));
				json.put("value", "");
				jsonArr.add(json);
			}
			
			codeList = productManager.selectPdToMrgCombo(vo);
			
			for(BgabGascpd04Dto targetBean : codeList){
				json = new JSONBaseVO();
				//json.put("key", StringUtil.isNullToStrTrm(targetBean.getLrg_cd()));
				json.put("value", StringUtil.isNullToStrTrm(targetBean.getMrg_cd()));
				json.put("name", StringUtil.isNullToStrTrm(targetBean.getMrg_nm()));
				
				jsonArr.add(json);
			}
			
			jso.put("MRG_COMB", jsonArr);
			
			modelAndView.addObject(JSON_DATA_KEY, jso.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	// 물품 조회
	@RequestMapping(value="/hncis/product/doSearchPdToProductList.do")
	public ModelAndView doSearchPdToProductList(HttpServletRequest req, HttpServletResponse res, 
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascpd01Dto dto = (BgabGascpd01Dto) getJsonToBean(paramJson, BgabGascpd01Dto.class);
		
		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }
		
		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = productManager.selectCountPdToProductList(dto);
		
		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));
		
		dto.setStartRow(startRow);
		dto.setEndRow(endRow);
		list.setRows(productManager.selectPdToProductList(dto));
		
		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
			
		return modelAndView;
	}

	// 물품 정보 조회
	@RequestMapping(value="/hncis/product/doSearchInfoPdToProductInfo.do")
	public ModelAndView doSearchInfoPdToProductInfo(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		BgabGascpd01Dto dto = (BgabGascpd01Dto) getJsonToBean(paramJson, BgabGascpd01Dto.class);
		
		BgabGascpd01Dto rsReqDto = new BgabGascpd01Dto();
		rsReqDto = productManager.selectInfoPdToProductInfo(dto);
		
		rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	// 물품 저장
	@RequestMapping(value="/hncis/product/doSavePdToProductInfo.do")
	public ModelAndView doSavePdToProductInfo(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws HncisException{
		List<BgabGascpd01Dto> dtoList = (List<BgabGascpd01Dto>) getJsonToList(paramJson, BgabGascpd01Dto.class);

		CommonMessage message = productManager.insertPdToProductInfo(dtoList);
		
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	
	// 물품 관리 조회
	@RequestMapping(value="/hncis/product/doSearchPdToProductMgmtList.do")
	public ModelAndView doSearchPdToProductMgmtList(HttpServletRequest req, HttpServletResponse res, 
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascpd01Dto dto = (BgabGascpd01Dto) getJsonToBean(paramJson, BgabGascpd01Dto.class);
		
		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }
		
		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = productManager.selectCountPdToProductMgmtList(dto);
		
		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));
		
		dto.setStartRow(startRow);
		dto.setEndRow(endRow);
		list.setRows(productManager.selectPdToProductMgmtList(dto));
		
		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
			
		return modelAndView;
	}

	// 물품 시리얼 번호 리스트 조회
	@RequestMapping(value="/hncis/product/doSearchPdListToSlrInfo.do")
	public ModelAndView doSearchPdListToSlrInfo(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		
		BgabGascpd01Dto vo = (BgabGascpd01Dto)getJsonToBean(paramJson, BgabGascpd01Dto.class);
		
		CommonList list = new CommonList();
		//검색
		list.setRows(productManager.doSearchPdListToSlrInfo(vo));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
		
		return modelAndView;
	}
	
	// 물품 삭제
	@RequestMapping(value="/hncis/product/doDeletePdToProductInfo.do")
	public ModelAndView doDeletePdToProductInfo(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{
		BgabGascpd01Dto dto = (BgabGascpd01Dto) getJsonToBean(paramJson, BgabGascpd01Dto.class);

		CommonMessage message = productManager.deletePdToProductInfo(dto);
		
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	// 물품 시리얼번호 콤보박스
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/product/doSearchSlrListCombo.do")
	public ModelAndView doSearchSlrListCombo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws Exception{

		BgabGascpd01Dto vo = (BgabGascpd01Dto)getJsonToBean(paramJson, BgabGascpd01Dto.class);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");

		try{
	 		JSONBaseVO jso = new JSONBaseVO();
	 		JSONBaseVO json = null;
			JSONBaseArray  jsonArr = null;

			List<BgabGascpd01Dto> codeList = null;

			jsonArr = new JSONBaseArray();
			json = new JSONBaseVO();
			json.put("name", HncisMessageSource.getMessage("select"));
			json.put("value", "");
			jsonArr.add(json);
			

			codeList = productManager.selectSlrListCombo(vo);

			for(BgabGascpd01Dto targetBean : codeList){
				json = new JSONBaseVO();
				json.put("value", StringUtil.isNullToStrTrm(targetBean.getSlr_no()));
				json.put("name", StringUtil.isNullToStrTrm(targetBean.getSlr_no()));

				jsonArr.add(json);
			}

			jso.put("SLR_COMB", jsonArr);

			modelAndView.addObject(JSON_DATA_KEY, jso.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return modelAndView;
	}

	// 물품 신청
	@RequestMapping(value="/hncis/product/doRequestPdToProduct.do")
	public ModelAndView doRequestBkToBook(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		BgabGascpd02Dto dto = (BgabGascpd02Dto) getJsonToBean(paramJson, BgabGascpd02Dto.class);

		CommonMessage message = productManager.updatePdToProductRequest(dto);
		
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		time = System.currentTimeMillis(); 
		dayTime = new SimpleDateFormat(strDateFormat); 
		strDT = dayTime.format(new Date(time)); 
		logger.info(strEnd + strDT);

		return modelAndView;
	}
	
	// 물품 이력 조회
	@RequestMapping(value="/hncis/product/doSearchPdToProductRentList.do")
	public ModelAndView doSearchPdToProductRentList(HttpServletRequest req, HttpServletResponse res, 
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		
		ModelAndView modelAndView = null;
		BgabGascpd02Dto dto = (BgabGascpd02Dto) getJsonToBean(paramJson, BgabGascpd02Dto.class);
		
		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }
		
		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = productManager.selectCountPdToProductRentList(dto);
		
		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));
		
		dto.setStartRow(startRow);
		dto.setEndRow(endRow);
		list.setRows(productManager.selectPdToProductRentList(dto));
		
		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
		return modelAndView;
	}
	
	// 물품 신청취소
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/product/doRentListToRequestCancel.do")
	public ModelAndView doRentListToRequestCancel(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="iParams" , required=true) String iParams) throws Exception{
		List<BgabGascpd02Dto> dtoList = (List<BgabGascpd02Dto>) getJsonToList(iParams, BgabGascpd02Dto.class);

		int cancelChk = productManager.deleteRentListToRequestCancel(dtoList);
		
		CommonMessage message = new CommonMessage();
		
		if (cancelChk > 0){
			for(BgabGascpd02Dto dto : dtoList){
				// BPM API호출
				String processCode = "P-C-005"; 	//프로세스 코드 (도서 프로세스) - 프로세스 정의서 참조
				String bizKey = dto.getDoc_no();	//신청서 번호
				String statusCode = "GASBZ01350010";	//액티비티 코드 (도서신청서작성) - 프로세스 정의서 참조
				String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디
		
				BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
			}
			message.setMessage(HncisMessageSource.getMessage("APPLY.0001"));
		} else {
			message.setMessage(HncisMessageSource.getMessage("APPLY.0003"));
		}
		
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	
	// 물품 대여
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/product/doRentPdToProductList.do")
	public ModelAndView doRentPdToProductList(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		
		List<BgabGascpd02Dto> dtoList = (List<BgabGascpd02Dto>)getJsonToList(paramJson, BgabGascpd02Dto.class);
		
		
		CommonMessage message = productManager.updatePdToProductRent(dtoList);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		time = System.currentTimeMillis(); 
		dayTime = new SimpleDateFormat(strDateFormat); 
		strDT = dayTime.format(new Date(time)); 
		logger.info(strEnd + strDT);
		
		return modelAndView;
	}
	
	// 물품 반납
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/product/doReturnPdToProductList.do")
	public ModelAndView doReturnPdToProductList(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		
		List<BgabGascpd02Dto> dtoList = (List<BgabGascpd02Dto>)getJsonToList(paramJson, BgabGascpd02Dto.class);
		
		CommonMessage message = productManager.updatePdToProductReturnList(dtoList);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	// 물품 반려
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/product/doRentCancelPdToProductList.do")
	public ModelAndView doRentCancelPdToProductList(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat(strDateFormat); 
		String strDT = dayTime.format(new Date(time)); 
		logger.info(strStart + strDT);
		
		List<BgabGascpd02Dto> dtoList = (List<BgabGascpd02Dto>)getJsonToList(paramJson, BgabGascpd02Dto.class);
		
		productManager.updatePdToProductRent(dtoList);
		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("RETURN.0004"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		time = System.currentTimeMillis(); 
		dayTime = new SimpleDateFormat(strDateFormat); 
		strDT = dayTime.format(new Date(time)); 
		logger.info(strEnd + strDT);
		
		return modelAndView;
	}
	
	/**
	 * product report save
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException 
	 */
	@RequestMapping(value="/hncis/product/doSearchPdToFile.do")
	public ModelAndView doSearchPdToFile(HttpServletRequest req, HttpServletResponse res, 
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascZ011Dto dto = (BgabGascZ011Dto) getJsonToBean(paramJson, BgabGascZ011Dto.class);
		
		CommonList list = new CommonList();
		list.setRows(productManager.getSelectPdToFile(dto));
		
		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
		
		return modelAndView;
	}
	
	/**
	 * product report save
	 * @param fileInfo
	 * @throws HncisException, IOException
	 */
	@RequestMapping(value="/hncis/product/doSavePdToFile.do")
	public void doSavePdToFile(HttpServletRequest req, HttpServletResponse res, 
		@RequestParam(value="fileInfo", required=true) String fileInfo)throws HncisException, IOException{
		
		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGascZ011Dto bgabGascZ011Dto = (BgabGascZ011Dto)getJsonToBean(fileInfo, BgabGascZ011Dto.class);
			productManager.savePdToFile(req, res, bgabGascZ011Dto);
		}
	}
	
	/**
	 * product report save
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/product/doDeletePdToFile.do")
	public ModelAndView doDeleteBkToFile(HttpServletRequest req, HttpServletResponse res, 
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{
		
		List<BgabGascZ011Dto> dto = (List<BgabGascZ011Dto>) getJsonToList(fileInfo, BgabGascZ011Dto.class);
		
		productManager.deletePdToFile(dto);
		
		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	/**
	 * product report save
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/product/doFileDown.do")
	public ModelAndView doFileDown(HttpServletRequest req, HttpServletResponse res, 
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{

		BgabGascZ011Dto dto = (BgabGascZ011Dto) getJsonToBean(fileInfo, BgabGascZ011Dto.class);
		BgabGascZ011Dto bgabGascZ011Dto = productManager.getSelectPdToFileInfo(dto);
		
		Map mpfileData = new HashMap();
		mpfileData.put("fileRealName",   bgabGascZ011Dto.getOgc_fil_nm());
		mpfileData.put("fileName",   bgabGascZ011Dto.getFil_nm());
		mpfileData.put("folderName",   "product");
		
		return new ModelAndView("download", "fileData", mpfileData);
	}
	
	/**
	 * accept excel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/hncis/product/doExcelproductList.excel")
	public ModelAndView doExcelproductList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		BgabGascpd02Dto bgabGascpd02Dto = (BgabGascpd02Dto) getJsonToBean(paramJson, BgabGascpd02Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = productManager.selectCountPdToProductRentList(bgabGascpd02Dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		bgabGascpd02Dto.setStartRow(1);
 		bgabGascpd02Dto.setEndRow(count);
 		//검색
 		list.setRows(productManager.selectPdToProductRentList(bgabGascpd02Dto));

		JSONArray gridData = JSONArray.fromObject(list.getRows());
		String[] headerTitleArray = header.replace("[","").replace("]","").split(",");
		String[] headerNameArray  = headerName.replace("[","").replace("]","").split(",");
		String[] fomatterArray    = fomatter.replace("[","").replace("]","").split(",");

		Map mpExcelData = new HashMap();
		mpExcelData.put("fileName",   fileName);
		mpExcelData.put("jobName",   "PD");
		mpExcelData.put("header",     headerTitleArray);
		mpExcelData.put("headerName", headerNameArray);
		mpExcelData.put("fomatter",   fomatterArray);
		mpExcelData.put("gridData",   gridData);

		return new ModelAndView("GridExcelView", "excelData", mpExcelData);
	}

}
