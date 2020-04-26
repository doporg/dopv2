package com.clsaa.dop.server.alert.service;

import com.clsaa.dop.server.alert.dao.ContactDao;
import com.clsaa.dop.server.alert.model.po.ContactPo;
import com.clsaa.dop.server.alert.model.vo.ContactVo;
import com.clsaa.rest.result.Pagination;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName ContactService
 * @Author
 * @Version 1.0
 * @Describtion TODO
 * @return
 * @since 2020-04-03
 **/

@Service
@Transactional
public class ContactService {
	@Autowired
	ContactDao contactDao;

	/**
	 * 新增联系人
	 * @param contact contactVo
	 * @param userId 用户id
	 */
	public void addNewContact(ContactVo contact, Long userId){
		ContactPo contactPo = new ContactPo();
		contactPo.setNewContact(contact, userId);
		System.out.println(contactPo.toString());
		contactDao.save(contactPo);

	}

	/**
	 * 修改联系人
	 * @param contact contactVo
	 */
	public void modifyContact(ContactVo contact){
		ContactPo contactPo = contactDao.findByCid(contact.getId());
		contactPo.setMtime(LocalDateTime.now());
		contactPo.setMail(contact.getMail());
		contactPo.setName(contact.getName());
		contactPo.setPhone(contact.getPhone());
		contactPo.setRemark(contact.getRemark());
		contactPo.setCid(contact.getId());
		contactDao.save(contactPo);
	}

	/**
	 * 修改联系人
	 * @param contact contactVo
	 * @param userId 用户id
	 */
	public void deleteContact(ContactVo contact, Long userId){
		ContactPo contactPo = contactDao.findByCid(contact.getId());
		contactPo.setDeleted(true);
		contactDao.save(contactPo);
	}


	/**
	 * 获取用户的所有告警联系人分页
	 * @param pageNo 页码
	 * @param pageSize 页数据数量
	 * @param userId 用户id
	 * @return
	 */

	public Pagination<ContactVo> getContactPagination(Integer pageNo, Integer pageSize, Long userId) {

		long cuser = 1;

		int count = (int) this.contactDao.count();

		Pagination<ContactVo> pagination = new Pagination<>();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setTotalCount(count);

		if (count == 0) {
			pagination.setPageList(Collections.emptyList());
			return pagination;
		}
		Sort sort = new Sort(Sort.Direction.DESC, "mtime");
		Pageable pageRequest = PageRequest.of(pagination.getPageNo() - 1, pagination.getPageSize(), sort);


		Specification<ContactPo> specification = new Specification<ContactPo>() {
			@Override
			public Predicate toPredicate(Root<ContactPo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Path<Long> cUser = root.get("cuser");
				Path<Integer> deleted = root.get("deleted");
				query.where(criteriaBuilder.equal(cUser,cuser),criteriaBuilder.equal(deleted,0));
				return null;
			}

		};
		List<ContactPo> contactPoList = this.contactDao.findAll(specification,pageRequest).getContent();

//		pagination.setPageList(userList.stream().map(u -> BeanUtils.convertType(u, UserV1.class)).collect(Collectors.toList()));
		List<ContactVo> contactVoList = new ArrayList<ContactVo>();
		for(ContactPo cp: contactPoList){
			System.out.println(cp.getCid());
			contactVoList.add(cp.transferToVo());
		}
		pagination.setPageList(contactVoList);
		return pagination;
	}

	public ContactVo getContactById(Long contactId) {
		return contactDao.findByCid(contactId).transferToVo();
	}

	public void deleteContacts(List<Long> contactIdList) {
		for(Long id : contactIdList){
			contactDao.deleteById(id);
		}
	}
}
